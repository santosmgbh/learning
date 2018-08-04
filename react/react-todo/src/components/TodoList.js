import React, {Component} from 'react'

export class TodoList extends Component {


    render() {
        return (
            <div>
                <label>
                    Nova tarefa:
                    <input autoFocus onKeyUp={this.props.onAddTask} type='text' />
                </label>
                <p>Minhas tarefas: </p>
                <ul>
                    {
                        this.props.tasks.map(task => {
                            return <li>{task}</li>
                        })
                    }
                </ul>
            </div>
        )
    }

}