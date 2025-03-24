import React from 'react';
import Navbar from './components/navbar/Navbar'
import CardList from './components/card_list/CardList';
import './App.css'

function App() {
  
  return (
    <div className="app">
      <Navbar />
      <div className='main-container'>
        <CardList />
      </div>
    </div>
  );
}

export default App;
