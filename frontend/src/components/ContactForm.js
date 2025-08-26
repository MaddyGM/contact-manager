import React, { useState, useEffect } from 'react';
import { createContact, getContact, updateContact, getPositions } from '../api/Contacts';
import { useNavigate, useParams } from 'react-router-dom';
import './ContactList.scss';

function ContactForm() {
    const [contact, setContact] = useState({ firstName: '', lastName: '', email: '', positionId: '' });
    const [positions, setPositions] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        const loadPositions = async () => {
            try {
                const response = await getPositions();
                setPositions(response.data);
            } catch (err) {
                console.error("Error loading positions:", err);
                setError('Error loading positions from server.');
            }
        };

        const loadContact = async () => {
            if (id) {
                setLoading(true);
                try {
                    const result = await getContact(id);
                    setContact(result.data);
                } catch (err) {
                    console.error(`Error loading contact with ID ${id}:`, err);
                    setError('Error loading contact details.');
                } finally {
                    setLoading(false);
                }
            }
        };

        loadPositions();
        loadContact();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setContact({ ...contact, [name]: value });
        setError('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        try {
            if (id) {
                await updateContact(id, contact);
            } else {
                await createContact(contact);
            }
            navigate('/');
        } catch (err) {
            console.error("Error submitting contact:", err);
            if (err.response && err.response.data) {
                const messages = Object.values(err.response.data).join(', ');
                setError(messages || 'Error saving data');
            } else {
                setError('Unexpected error occurred while saving contact.');
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-4">
            <div className="row justify-content-center">
                <div className="col-12 col-sm-10 col-md-8 col-lg-6">
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
                        <div className="mb-3">
                            <label className="form-label">Position</label>
                            <select
                                className="form-control"
                                name="positionId"
                                value={contact.positionId}
                                onChange={handleChange}
                                required
                            >
                                <option value="">-- Select a position --</option>
                                {positions.map((pos) => (
                                    <option key={pos.id} value={pos.id}>
                                        {pos.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button type="submit" className="btn btn-success" disabled={loading}>
                            {id ? 'Update' : 'Create'}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default ContactForm;