// Task 4: Object-Oriented Programming
// Create a class `Car` with properties `brand` and `speed`, and a method `accelerate()` that increases speed by 10

// Define the Car class
class Car {
    constructor(brand, speed = 0) {
        this.brand = brand;
        this.speed = speed;
    }

    // Method to increase speed by 10
    accelerate() {
        this.speed += 10;
        return this.speed;
    }

    // Method to get car details as a string
    getDetails() {
        return `${this.brand} - Current Speed: ${this.speed} km/h`;
    }
}

// Variables to store the current car instance
let currentCar = null;
const outputDiv = document.getElementById('output');
const createBtn = document.getElementById('createBtn');
const accelerateBtn = document.getElementById('accelerateBtn');

// Event listener for Create Car button
createBtn.addEventListener('click', function() {
    const brandInput = document.getElementById('brand').value.trim();
    const speedInput = parseInt(document.getElementById('speed').value) || 0;
    
    if (brandInput) {
        // Create a new Car instance
        currentCar = new Car(brandInput, speedInput);
        
        // Update UI
        outputDiv.innerHTML = `<p>Car created: ${currentCar.getDetails()}</p>`;
        accelerateBtn.disabled = false;
    } else {
        alert('Please enter a car brand!');
    }
});

// Event listener for Accelerate button
accelerateBtn.addEventListener('click', function() {
    if (currentCar) {
        currentCar.accelerate();
        outputDiv.innerHTML += `<p>Accelerated: ${currentCar.getDetails()}</p>`;
    }
});
