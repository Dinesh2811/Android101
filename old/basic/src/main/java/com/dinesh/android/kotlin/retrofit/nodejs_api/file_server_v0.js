const express = require('express');
const app = express();
const path = require('path');
const fs = require('fs');

// Define a route for downloading the file
app.get('/download', (req, res) => {
  const filePath = 'D:/api/sharedfile';
  const fileExtension = path.extname(filePath);
  const defaultExtension = 'dat'; // Default extension if file doesn't have one

  // Check if the file exists
  fs.access(filePath, fs.constants.F_OK, (err) => {
    if (err) {
      console.error('File not found:', err);
      return res.status(404).send('File not found');
    }

    // Set the appropriate content type based on the file extension
    const contentType = getContentType(fileExtension);

    // Set the filename for download (without extension if it doesn't exist)
    const filename = path.basename(filePath, fileExtension) || path.basename(filePath, defaultExtension);

    // Set the headers for the download response
    res.set({
      'Content-Type': contentType,
      'Content-Disposition': `attachment; filename="${filename}"`,
    });

    // Stream the file to the response
    const fileStream = fs.createReadStream(filePath);
    fileStream.pipe(res);
  });

  res.on('error', (err) => {
    // Handle connection abort error
    console.error('Error downloading file:', err);
    if (!res.headersSent) {
      res.status(500).send('Internal Server Error');
    }
  });
});

// Helper function to get the content type based on file extension
function getContentType(extension) {
  switch (extension) {
    case '.mp4':
      return 'video/mp4';
    case '.mkv':
      return 'video/x-matroska';
    case '.mp3':
      return 'audio/mpeg';
    case '.wav':
      return 'audio/wav';
    case '.txt':
      return 'text/plain';
    default:
      return 'application/octet-stream';
  }
}

// Start the server
const port = 3000; // You can change this to any available port you prefer
app.listen(port, () => {
  console.log(`API server is running on http://localhost:${port}`);
});
