import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';

const Navbar = () => {
  return (
    <AppBar position="sticky" sx={{ top: 0, backgroundColor: '#424242' }}>
      <Toolbar sx={{ justifyContent: 'center', minHeight: '64px' }}>
        <Typography
          variant="h6"
          sx={{
            fontWeight: 'bold',
            display: 'flex',
            alignItems: 'center',
            gap: 1
          }}
        >
          📘 Test Generator 📝
        </Typography>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
