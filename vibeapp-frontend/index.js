const express = require('express');
const axios = require('axios');
const path = require('path');

const app = express();
const PORT = 3000;
const BACKEND_URL = 'http://localhost:8080/api';

app.use(express.static('public'));
app.use(express.json());

// 게시글 목록 조회 API (Backend Proxy)
app.get('/api/posts', async (req, res) => {
    try {
        const response = await axios.get(`${BACKEND_URL}/posts`);
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching posts:', error.message);
        res.status(500).json({ error: 'Failed to fetch posts from backend' });
    }
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

app.listen(PORT, () => {
    console.log(`Frontend server is running on http://localhost:${PORT}`);
});
