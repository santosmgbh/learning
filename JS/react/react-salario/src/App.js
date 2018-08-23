import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Calculo } from './components/Calculo';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';


class App extends Component {

  
  render() {

    const divStyle = {
      color: 'blue',
      height:'500px'
    };

    return (
      <div className="App">
        <Grid container spacing={24}  >
          <Grid item xs={6} >
            <Paper style={divStyle}>
              <Calculo></Calculo>
            </Paper>
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default App;
