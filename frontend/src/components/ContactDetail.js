import React, { useEffect, useState } from 'react';
import { getContact } from '../api/Contacts';
import { useParams, Link } from 'react-router-dom';

function ContactDetail() {
    const { id } = useParams();
    const [contact, setContact] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadContact = async () => {
            setLoading(true);
            setError(null);
            try {
                console.log(`Loading contact with ID: ${id}...`);
                const result = await getContact(id);
                if (!result.data) {
                    setError("Contact not found.");
                } else {
                    setContact(result.data);
                    console.log("Contact loaded successfully:", result.data);
                }
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
    }, [id]);

    if (loading) return <div className="container mt-4">Loading contact...</div>;
    if (error) return <div className="container mt-4 text-danger">{error}</div>;
    if (!contact) return <div className="container mt-4">No contact data available.</div>;

    return (
        <div className="container w-25 mt-4 d-flex flex-column justify-content-center">
            <h2>Contact details</h2>
            <div className="card">
                <div className="card-body">
                    <p><strong>Name:</strong> {contact.firstName}</p>
                    <p><strong>Lastname:</strong> {contact.lastName}</p>
                    <p><strong>Email:</strong> {contact.email}</p>
                </div>
            </div>
            <Link className="btn btn-secondary mt-3" to="/">Back to contacts page</Link>
        </div>
    );
}

export default ContactDetail;
