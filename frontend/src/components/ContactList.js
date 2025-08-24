import React, { useCallback, useEffect, useState } from 'react';
import { getContacts, deleteContact } from '../api/Contacts';
import { Link } from 'react-router-dom';
import './ContactList.scss';

function ContactList() {
    const [contacts, setContacts] = useState([]);
    const [loading, setLoading] = useState(false);

    const loadContacts = useCallback(async () => {
        setLoading(true);
        try {
            console.log("Loading contacts from API...");
            const result = await getContacts();
            setContacts(result.data);
            console.log(`Contacts loaded successfully: ${result.data.length} entries.`);
        } catch (error) {
            console.error("Error loading contacts:", error);
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
        } catch (error) {
            console.error(`Error deleting contact with ID ${id}:`, error);
        }
    };

    return (
        <div className="container mt-4 d-flex flex-column align-items-center min-vh-100 ">
            <div className="w-100">
                <div className="d-flex flex-row gap-5 justify-content-between align-items-center mt-5 mb-3">
                    <h2>Contacts</h2>
                    <Link className="btn editButton mb-3" to="/add">Add Contact</Link>
                </div>

                {loading ? (
                    <div>Loading contacts...</div>
                ) : (
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
