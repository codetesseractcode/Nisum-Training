// Static weather data for different countries
const weatherData = [
    {
        country: "USA",
        temperature: "24°C",
        humidity: "45%",
        wind: "10 km/h",
        precipitation: "5%",
        uvIndex: "High"
    },
    {
        country: "India",
        temperature: "32°C",
        humidity: "70%",
        wind: "6 km/h",
        precipitation: "15%",
        uvIndex: "Very High"
    },
    {
        country: "UK",
        temperature: "18°C",
        humidity: "80%",
        wind: "15 km/h",
        precipitation: "30%",
        uvIndex: "Low"
    },
    {
        country: "Australia",
        temperature: "28°C",
        humidity: "50%",
        wind: "12 km/h",
        precipitation: "2%",
        uvIndex: "Extreme"
    }
];

// Function to update weather details
function updateWeather(country) {
    // Find the country in our data
    const data = weatherData.find(item => 
        item.country.toLowerCase() === country.toLowerCase()
    );

    if (data) {
        // Update the DOM with the weather information
        document.querySelector('.temperature').textContent = data.temperature;
        document.querySelector('.location').textContent = data.country;
        document.getElementById('humidity').textContent = data.humidity;
        document.getElementById('wind').textContent = data.wind;
        document.getElementById('precipitation').textContent = data.precipitation;
        document.getElementById('uvIndex').textContent = data.uvIndex;
        
        return true;
    }
    
    return false;
}

// Event listener for the input field
document.getElementById('countryInput').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        const country = this.value.trim();
        
        if (country) {
            const found = updateWeather(country);
            
            if (!found) {
                alert('Country not found in our database!');
            }
        }
    }
});
