# 📡 LogBeam: A Serverless Logging System for IoT Devices

A serverless logging platform that enables IoT devices to send telemetry data via HTTP or MQTT, which is processed and stored efficiently in a columnar time-series database using AWS services.

---

## 🚀 Key Features

- Dual Input Methods: Devices can send data via HTTP or MQTT.
- Time-Series Storage: Efficient logging using Amazon Timestream, a columnar database for time-series data.
- Serverless Architecture: No infrastructure to manage — uses Lambda, API Gateway, IoT Core, and Timestream.
- Real-Time Logging: Supports structured logs with device ID, temperature, humidity, and timestamp.
- Device Simulation: Works with real IoT devices or simulated data from a developer’s laptop.

---

## 📍 Use Cases

- IoT Monitoring: Track and analyze environmental data like temperature and humidity.
- Device Prototyping: Use your laptop to simulate sensor logs during development.
- Smart Infrastructure: Log building, machinery, or sensor data for diagnostics.
- Environmental Systems: Monitor air, water, or climate data from remote sensors.
- Data Collection Pipelines: Feed high-resolution device logs into analytics platforms.

---

## 🧠 Architecture

The system consists of an HTTP API and MQTT channel, both triggering a serverless function that logs data into a time-series database. API Gateway handles HTTP, and AWS IoT Core manages MQTT messages. AWS Lambda serves as the processing layer, while Amazon Timestream stores the telemetry.

---

## ⚙️ Technology Stack

| Layer       | Technology        |
|-------------|-------------------|
| Input       | HTTP / MQTT       |
| Processing  | AWS Lambda        |
| Messaging   | AWS IoT Core      |
| HTTP API    | Amazon API Gateway|
| Database    | Amazon Timestream |
| Language    | Java              |
| Build Tool  | Maven             |

---

## 🔌 API Endpoints

**HTTP Ingestion**

- POST `/log`  
  Accepts a structured telemetry log from the IoT device.

**MQTT Ingestion**

- Topic: `iot/logs/{deviceId}`  
  Accepts the same structured log via MQTT published from a device or simulator.

Each method delivers telemetry including device ID, temperature, humidity, and timestamp.

---

## 📦 Logging Logic

All data is stored in a time-series format with:

- Device ID as a dimension.
- Temperature and humidity as individual measures.
- Timestamp formatted in ISO 8601 for time-based querying.
- Data organized and stored in Amazon Timestream for optimized columnar access.

---

## 🛡️ Notes

- Supports both real IoT hardware and development simulations (e.g., curl or MQTT client from laptop).
- MQTT access is protected using AWS IoT device certificates.
- HTTP access can be secured using API Gateway features like API keys or IAM.
- Timestamp should be sent in UTC ISO 8601 format.
- Designed for easy expansion to support additional metrics or devices.

---

