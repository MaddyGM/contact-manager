import axios from 'axios';

const API_URL = 'http://localhost:8080/api/contacts';
const POSITIONS_API_URL = 'http://localhost:8080/api/positions';

export const getContacts = () => axios.get(API_URL);
export const getContact = (id) => axios.get(`${API_URL}/${id}`);
export const createContact = (contact) => axios.post(API_URL, contact);
export const updateContact = (id, contact) => axios.put(`${API_URL}/${id}`, contact);
export const deleteContact = (id) => axios.delete(`${API_URL}/${id}`);

export const getPositions = () => axios.get(POSITIONS_API_URL);
export const getPosition = (id) => axios.get(`${POSITIONS_API_URL}/${id}`);
export const createPosition = (position) => axios.post(POSITIONS_API_URL, position);
export const updatePosition = (id, position) => axios.put(`${POSITIONS_API_URL}/${id}`, position);
export const deletePosition = (id) => axios.delete(`${POSITIONS_API_URL}/${id}`);