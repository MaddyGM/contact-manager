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
        <div className="container mt-4 d-flex flex-column align-items-center min-vh-100">
            <div className="w-100">
                <div className="row mt-5 mb-3">
                    <div className="col-12 col-md-6 d-flex align-items-center">
                        <h2 className="mb-0">Contacts</h2>
                    </div>
                    <div className="col-12 col-md-6 d-flex justify-content-md-end justify-content-start mt-2 mt-md-0">
                        <Link className="btn editButton btn-sm mb-3" to="/add">Add Contact</Link>
                    </div>
                </div>

                {loading && <div>Loading contacts...</div>}

                {error && <div className="text-danger mb-3">{error}</div>}

                {!loading && !error && (
                    <div className="table-responsive">
                        <table className="table table-striped">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Last Name</th>
                                <th>Email</th>
                                <th>Position</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            {contacts.map(contact => (
                                <tr key={contact.id}>
                                    <td>{contact.firstName}</td>
                                    <td>{contact.lastName}</td>
                                    <td>{contact.email}</td>
                                    <td>{contact.position ? contact.position.name : ''}</td>
                                    <td>
                                        <div className="d-flex flex-wrap gap-1">
                                            <Link className="btn editButton btn-sm me-1 mb-1" to={`/edit/${contact.id}`}>Edit</Link>
                                            <Link className="btn editButton btn-sm me-1 mb-1" to={`/detail/${contact.id}`}>Details</Link>
                                            <button className="btn deleteButton btn-sm mb-1" onClick={() => handleDelete(contact.id)}>Delete</button>
                                        </div>
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