import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const ProfilePage = () => {
    const [user, setUser] = useState(null);
    const [error, setError] = useState(null);
    const [isGeneratingReport, setIsGeneratingReport] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const response = await ApiService.getUserProfile();
                // Fetch user bookings using the fetched user ID
                const userPlusBookings = await ApiService.getUserBookings(response.user.id);
                setUser(userPlusBookings.user)

            } catch (error) {
                setError(error.response?.data?.message || error.message);
            }
        };

        fetchUserProfile();
    }, []);

    const handleLogout = () => {
        ApiService.logout();
        navigate('/home');
    };

    const handleEditProfile = () => {
        navigate('/edit-profile');
    };


    // Nova função para gerar e baixar o relatório em PDF
    const handleGenerateReport = async () => {
        // Verifica se os dados do usuário estão disponíveis
        if (!user || !user.name) {
            setError("User data not available to generate report.");
            return;
        }

        setIsGeneratingReport(true); // Ativa o estado de loading
        setError(null);

        try {
            // Chama o método do serviço que busca o PDF
            // Assumindo que seu ApiService foi atualizado
            const response = await ApiService.generateLoginReport(user.name);

            // Cria uma URL temporária para o arquivo (blob) recebido
            const url = window.URL.createObjectURL(new Blob([response.data]));
            
            // Cria um elemento de link <a> temporário
            const link = document.createElement('a');
            link.href = url;
            
            // Define o nome do arquivo que será baixado
            const filename = `historico-login-${user.name}.pdf`;
            link.setAttribute('download', filename);
            
            // Adiciona o link ao documento e o clica para iniciar o download
            document.body.appendChild(link);
            link.click();
            
            // Limpa o link e a URL da memória após o download
            link.parentNode.removeChild(link);
            window.URL.revokeObjectURL(url);

        } catch (err) {
            setError("Failed to generate report. Please try again later.");
            console.error(err);
        } finally {
            setIsGeneratingReport(false); // Desativa o estado de loading
        }
    };

    return (
        <div className="profile-page">
            {user && <h2>Welcome, {user.name}</h2>}
            <div className="profile-actions">
                <button className="edit-profile-button" onClick={handleEditProfile}>Edit Profile</button>
                <button className="logout-button" onClick={handleLogout}>Logout</button>
            </div>
            {error && <p className="error-message">{error}</p>}
            {user && (
                <div className="profile-details">
                    <h3>My Profile Details</h3>
                    <p><strong>Email:</strong> {user.email}</p>
                    <p><strong>Phone Number:</strong> {user.phoneNumber}</p>
                {/* Botão para gerar o relatório */}
                    <button 
                        className="generate-report-button" 
                        onClick={handleGenerateReport} 
                        disabled={isGeneratingReport}
                    >
                        {isGeneratingReport ? 'Generating Report...' : 'Generate Access Report'}
                    </button>
                </div>
            )}
            <div className="bookings-section">
                <h3>My Booking History</h3>
                <div className="booking-list">
                    {user && user.bookings.length > 0 ? (
                        user.bookings.map((booking) => (
                            <div key={booking.id} className="booking-item">
                                <p><strong>Booking Code:</strong> {booking.bookingConfirmationCode}</p>
                                <p><strong>Check-in Date:</strong> {booking.checkInDate}</p>
                                <p><strong>Check-out Date:</strong> {booking.checkOutDate}</p>
                                <p><strong>Total Guests:</strong> {booking.totalNumOfGuest}</p>
                                <p><strong>Room Type:</strong> {booking.room.roomType}</p>
                                <img src={booking.room.roomPhotoUrl} alt="Room" className="room-photo" />
                            </div>
                        ))
                    ) : (
                        <p>No bookings found.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;
