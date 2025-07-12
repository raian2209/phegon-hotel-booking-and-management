import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from '../../service/ApiService';

const AdminPage = () => {
    const [adminName, setAdminName] = useState('');
    const navigate = useNavigate();
    // eslint-disable-next-line
    const [error, setError] = useState(null);

    const handleGenerateReport = async () => {
        try {
            const response = await ApiService.generateBookingReport();
            
            // Lógica para criar e acionar o download do arquivo no navegador
            const blob = new Blob([response.data], { type: 'application/pdf' });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `relatorio_reservas_${new Date().toISOString().slice(0,10)}.pdf`);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url); // Libera a memória

        } catch (error) {
            console.error("Erro ao gerar relatório:", error.message);
            setError("Não foi possível gerar o relatório.");
        }
    };


    useEffect(() => {
        const fetchAdminName = async () => {
            try {
                const response = await ApiService.getUserProfile();
                setAdminName(response.user.name);
            } catch (error) {
                console.error('Error fetching admin details:', error.message);
            }
        };

        fetchAdminName();
    }, []);

    return (
        <div className="admin-page">
            <h1 className="welcome-message">Welcome, {adminName}</h1>
            <div className="admin-actions">
                <button className="admin-button" onClick={() => navigate('/admin/manage-rooms')}>
                    Manage Rooms
                </button>
                <button className="admin-button" onClick={() => navigate('/admin/manage-bookings')}>
                    Manage Bookings
                </button>
                    <button className="admin-button" onClick={handleGenerateReport}>
                    Gerar Relatório de Reservas
                </button>
            </div>
        </div>
    );
}

export default AdminPage;
