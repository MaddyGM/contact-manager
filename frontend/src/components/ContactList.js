import React, {useCallback, useEffect, useState} from 'react';
import { getContacts, deleteContact } from '../api/Contacts';
import { Link } from 'react-router-dom';
import './ContactList.scss';

function ContactList() {
    const [contacts, setContacts] = useState([]);

    const loadContacts = useCallback(async () => {
        const result = await getContacts();
        setContacts(result.data);
    }, []);

    useEffect(() => {
       loadContacts();
    }, [loadContacts]);


    const handleDelete = async (id) => {
        await deleteContact(id);
        await loadContacts();
    };

    return (
        <div className="container mt-4">

            <div className="h-75 w-75 align-items-center">
                <div className="d-flex flex-row gap-5 justify-content-between align-items-center mt-5 mb-3">
                    <h2>Contacts</h2>
                    <Link className="btn btn-primary mb-3" to="/add">Add Contact</Link>
                </div>

                <div className="colorTable">
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {contacts.map(contact => (
                            <tr key={contact.id}>
                                <td>{contact.firstName}</td>
                                <td>{contact.lastName}</td>
                                <td>{contact.email}</td>
                                <td>
                                    <Link className="btn btn-info me-2 editButton" to={`/edit/${contact.id}`}>Edit</Link>
                                    <button className="deleteButton btn btn-danger " onClick={() => handleDelete(contact.id)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>


            </div>

        </div>
    );
}

export default ContactList;
