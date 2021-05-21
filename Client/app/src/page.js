import React from 'react'
import logo from './logo.svg'
import './App.css';

export default function Page({header, children}){
    return(
        <div className="App">
            <header className="App-header">
                {header && <h1>{header}</h1>}
                {children}
            </header>
        </div>
    );
}