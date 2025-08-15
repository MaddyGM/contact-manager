import React, { useEffect, useState } from 'react';
import { getContact } from '../api/Contacts';
import { useParams, Link } from 'react-router-dom';

function ContactDetail() {
    const { id } = useParams();
    const [contact, setContact] = useState(null);

    useEffect(() => {
        const loadContact = async () => {
            const result = await getContact(id);
            setContact(result.data);
        };
        loadContact();
    }, [id]);


    if (!contact) return <div className="container mt-4">Cargando...</div>;

    return (
        <div className="container mt-4">
            <h2>Detalle del Contacto</h2>
            <div className="card">
                <div className="card-body">
                    <p><strong>Nombre:</strong> {contact.firstName}</p>
                    <p><strong>Apellido:</strong> {contact.lastName}</p>
                    <p><strong>Email:</strong> {contact.email}</p>
                </div>
            </div>
            <Link className="btn btn-secondary mt-3" to="/">Volver</Link>
        </div>
    );
}

export default ContactDetail;
