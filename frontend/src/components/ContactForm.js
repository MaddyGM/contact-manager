import React, { useState, useEffect } from 'react';
import { createContact, getContact, updateContact } from '../api/Contacts';
import { useNavigate, useParams } from 'react-router-dom';
import './ContactList.scss';

function ContactForm() {
    const [contact, setContact] = useState({ firstName: '', lastName: '', email: '' });
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            const loadContact = async () => {
                const result = await getContact(id);
                setContact(result.data);
            };
            loadContact();
        }
    }, [id]);



    const handleChange = e => {
        setContact({ ...contact, [e.target.name]: e.target.value });
    };

    const handleSubmit = async e => {
        e.preventDefault();
        if (id) await updateContact(id, contact);
        else await createContact(contact);
        navigate('/');
    };

    return (
        <div className="container mt-4">
            <h2>{id ? 'Editar Contacto' : 'Agregar Contacto'}</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">Name</label>
                    <input className="form-control" name="firstName" value={contact.firstName} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                    <label className="form-label">Last Name</label>
                    <input className="form-control" name="lastName" value={contact.lastName} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                    <label className="form-label">Email</label>
                    <input className="form-control" type="email" name="email" value={contact.email} onChange={handleChange} required />
                </div>
                <button type="submit" className="btn btn-success">Save</button>
            </form>
        </div>
    );
}

export default ContactForm;
