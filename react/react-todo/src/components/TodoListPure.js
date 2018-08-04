import React from 'react'

export const TodoListPure = (props) => {
    return (
        <div>
            <label>
                Nova tarefa:
                <input autoFocus onKeyUp={props.onAddTask} type='text' />
            </label>
            <p>Minhas tarefas: </p>
            <ul>
                {
                    props.tasks.map(task => {
                        return <li>{task}</li>
                    })
                }
            </ul>
        </div>
    )
}

