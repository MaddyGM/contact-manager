import React, { useState, useEffect } from 'react';
import { createContact, getContact, updateContact } from '../api/Contacts';
import { useNavigate, useParams } from 'react-router-dom';
import './ContactList.scss';

function ContactForm() {
    const [contact, setContact] = useState({ firstName: '', lastName: '', email: '' });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            const loadContact = async () => {
                setLoading(true);
                setError(null);
                try {
                    console.log(`Loading contact with ID: ${id}...`);
                    const result = await getContact(id);
                    setContact(result.data);
                    console.log("Contact loaded successfully:", result.data);
                } catch (err) {
                    console.error(`Error loading contact with ID ${id}:`, err);
                    if (err.response && err.response.data && err.response.data.message) {
                        setError(err.response.data.message);
                    } else {
                        setError("Unexpected error occurred while loading contact.");
                    }
                } finally {
                    setLoading(false);
                }
            };
            loadContact();
        }
    }, [id]);

    const handleChange = e => {
        setContact({ ...contact, [e.target.name]: e.target.value });
        setError(''); // Clear error on input change
    };

    const handleSubmit = async e => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        try {
            if (id) {
                console.log(`Updating contact ID: ${id}...`);
                await updateContact(id, contact);
            } else {
                console.log("Creating new contact...");
                await createContact(contact);
            }
            navigate('/');
        } catch (err) {
            console.error("Error submitting contact:", err);
            if (err.response && err.response.data) {
                const messages = Object.values(err.response.data).join(', ');
                setError(messages || 'Error sending data');
            } else {
                setError('Unexpected error occurred while sending contact data.');
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-4 w-25">
            <h2>{id ? 'Editar Contacto' : 'Agregar Contacto'}</h2>

            {loading && <div className="mb-3">Loading...</div>}
            {error && <div className="text-danger mb-3">{error}</div>}

            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">Name</label>
                    <input
                        className="form-control"
                        name="firstName"
                        value={contact.firstName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Last Name</label>
                    <input
                        className="form-control"
                        name="lastName"
                        value={contact.lastName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Email</label>
                    <input
                        className="form-control"
                        type="email"
                        name="email"
                        value={contact.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-success" disabled={loading}>
                    {id ? 'Update' : 'Create'}
                </button>
            </form>
        </div>
    );
}

export default ContactForm;
