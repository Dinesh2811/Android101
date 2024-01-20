const express = require('express');
const fs = require('fs/promises');
const path = require('path');
const morgan = require('morgan');

const app = express();
const port = process.env.PORT || 3000;
const dbPath = path.resolve(__dirname, 'db.json');

app.use(express.json());

// Logging middleware
app.use(morgan('dev'));

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err);
  res.status(500).json({ error: 'Internal server error' });
});

// GET request handler
app.get('/api/:endpoint', async (req, res, next) => {
  try {
    const { endpoint } = req.params;
    const jsonData = await readDatabase();

    if (jsonData.hasOwnProperty(endpoint)) {
      const result = jsonData[endpoint];
      res.json(result);
    } else {
      res.status(404).json({ error: 'Endpoint not found' });
    }
  } catch (err) {
    next(err);
  }
});

// POST request handler
app.post('/api/:endpoint', async (req, res, next) => {
  try {
    const { endpoint } = req.params;
    const jsonData = await readDatabase();

    const requestBody = req.body;
    if (!isValidRequestBody(requestBody)) {
      return res.status(400).json({ error: 'Invalid request body' });
    }

    jsonData[endpoint] = requestBody;

    await writeDatabase(jsonData);
    res.status(201).json({ message: 'Data successfully updated' });
  } catch (err) {
    next(err);
  }
});

// DELETE request handler
app.delete('/api/:endpoint', async (req, res, next) => {
  try {
    const { endpoint } = req.params;
    const jsonData = await readDatabase();

    if (!jsonData.hasOwnProperty(endpoint)) {
      return res.status(404).json({ error: 'Endpoint not found' });
    }

    delete jsonData[endpoint];

    await writeDatabase(jsonData);
    res.json({ message: 'Data successfully deleted' });
  } catch (err) {
    next(err);
  }
});

// Function to read the database file
async function readDatabase() {
  const data = await fs.readFile(dbPath, 'utf8');
  return JSON.parse(data);
}

// Function to write to the database file
async function writeDatabase(jsonData) {
  await fs.writeFile(dbPath, JSON.stringify(jsonData, null, 2));
}

// Function to validate the request body
function isValidRequestBody(requestBody) {
  return typeof requestBody === 'object' && requestBody !== null;
}

// Start the server
app.listen(port, () => {
  console.log(`API server listening at http://localhost:${port}`);
});
