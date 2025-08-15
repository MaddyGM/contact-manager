import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ContactList from './components/ContactList';
import ContactForm from './components/ContactForm';
import ContactDetail from "./components/ContactDetail";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<ContactList />} />
                <Route path="/add" element={<ContactForm />} />
                <Route path="/edit/:id" element={<ContactForm />} />
                <Route path="/detail/:id" element={<ContactDetail />} />
            </Routes>
        </Router>
    );
}

export default App;
