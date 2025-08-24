import React, { useCallback, useEffect, useState } from 'react';
import { getContacts, deleteContact } from '../api/Contacts';
import { Link } from 'react-router-dom';
import './ContactList.scss';

function ContactList() {
    const [contacts, setContacts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const loadContacts = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            console.log("Loading contacts from API...");
            const result = await getContacts();
            setContacts(result.data);
            console.log(`Contacts loaded successfully: ${result.data.length} entries.`);
        } catch (err) {
            console.error("Error loading contacts:", err);
            if (err.response && err.response.data && err.response.data.message) {
                setError(err.response.data.message);
            } else {
                setError("Unexpected error occurred while loading contacts.");
            }
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        loadContacts();
    }, [loadContacts]);

    const handleDelete = async (id) => {
        const confirmDelete = window.confirm("Do you really want to delete this contact?");
        if (!confirmDelete) return;

        try {
            console.log(`Delete contact with ID: ${id}...`);
            await deleteContact(id);
            console.log(`Contact with ID ${id} deleted successfully.`);
            await loadContacts();
        } catch (err) {
            console.error(`Error deleting contact with ID ${id}:`, err);
            if (err.response && err.response.data && err.response.data.message) {
                setError(err.response.data.message);
            } else {
                setError(`Unexpected error occurred while deleting contact with ID ${id}.`);
            }
        }
    };

    return (
        <div className="container mt-4 d-flex flex-column align-items-center min-vh-100 ">
            <div className="w-100">
                <div className="d-flex flex-row gap-5 justify-content-between align-items-center mt-5 mb-3">
                    <h2>Contacts</h2>
                    <Link className="btn editButton mb-3" to="/add">Add Contact</Link>
                </div>

                {loading && <div>Loading contacts...</div>}

                {error && <div className="text-danger mb-3">{error}</div>}

                {!loading && !error && (
                    <div className="colorTable table-responsive">
                        <table className="table table-striped">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Last Name</th>
                                <th>Email</th>
                                <th>Position</th>
                                <th> </th>
                            </tr>
                            </thead>
                            <tbody>
                            {contacts.map(contact => (
                                <tr key={contact.id}>
                                    <td>{contact.firstName}</td>
                                    <td>{contact.lastName}</td>
                                    <td>{contact.email}</td>
                                    <td> </td>
                                    <td>
                                        <Link className="btn editButton me-2" to={`/edit/${contact.id}`}>Edit</Link>
                                        <Link className="btn editButton me-2" to={`/detail/${contact.id}`}>Details</Link>
                                        <button className="btn deleteButton" onClick={() => handleDelete(contact.id)}>Delete</button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    );
}

export default ContactList;
