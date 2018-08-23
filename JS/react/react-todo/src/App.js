import React, { Component } from 'react';
import {TodoListPure} from './components/TodoListPure'
import logo from './logo.svg';
import './App.css';

class App extends Component {

  constructor() {
    super();

    this.state = {
      tasks: ['Estudar', 'Correr', 'Video game', 'IGTI']
    }
  }

  addTask(event) {
    if(event.keyCode !== 13) return;

    const newTask = event.target.value;
    
    // para alterar o state não pode se alterar o objeto diretamente, é necessário chamar o setState    
    this.setState(
      {
        tasks: [...this.state.tasks, newTask] // ... spread operator: ele clona a lista atual e adiciona o newTask no final
      }
    ) 
  }

  render() {
    return (
      <div className="App">
        <h1>React Todo</h1>
        {/* É necessário passar a função com arrow function para carregar o contexto do componente pai */}
        <TodoListPure onAddTask={(event) => this.addTask(event)} tasks={this.state.tasks}/>
      </div>
    );
  }
}

export default App;
