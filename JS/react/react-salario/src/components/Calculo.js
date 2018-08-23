
import React, { Component } from 'react';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Input from '@material-ui/core/Input';
import InputAdornment from '@material-ui/core/InputAdornment';
import { Button } from '../../node_modules/@material-ui/core';

const TETO_INSS_2018 = 621.04;

const tabelaINSS_2018 = [
    {
        ate: 1693.72,
        aliquota: 8,
    },
    {
        ate: 2822.9,
        aliquota: 9,
    },
    {
        ate: 5645.8,
        aliquota: 11,
    },
    {
        ate: Number.MAX_SAFE_INTEGER,
        aliquota: 11,
    },
];

const tabelaIRPF_2018 = [
    {
        ate: 1903.98,
        aliquota: 0,
        deducao: 0,
    },
    {
        ate: 2826.65,
        aliquota: 7.5,
        deducao: 142.8,
    },
    {
        ate: 3751.05,
        aliquota: 15.0,
        deducao: 354.8,
    },
    {
        ate: 4664.68,
        aliquota: 22.5,
        deducao: 636.13,
    },
    {
        ate: Number.MAX_SAFE_INTEGER,
        aliquota: 27.5,
        deducao: 869.36,
    },
];

const styles = theme => ({
    margin: {
        margin: theme.spacing.unit,
    }
});

export class Calculo extends Component {

    constructor() {
        super();

        this.state = {
            salario: null,
            baseInss: 0.00,
            descontoInss: 0.00,
            baseIrpf: 0.00,
            descontoIrpf: 0.00,
            salarioLiquido: 0.00
        }
    }

    _onSalarioChange = (event) => {
        let salario = parseInt(event.target.value);
        if (!event.target.value) {
            salario = 0.00;
        }
        this.setState(this._calculaSalario(salario));
    };

    _calculaSalario = (salario) => {        
        const baseInss = salario;
        const descontoInss = this._calculaDescontoInss(baseInss);
        const baseIrpf = salario - descontoInss;
        const descontoIrpf = this._calculaDescontoIrpf(baseIrpf);
        const salarioLiquido = salario - descontoInss - descontoIrpf;

        return {
            salario: salario,
            baseInss: baseInss,
            descontoInss: descontoInss,
            baseIrpf: baseIrpf,
            descontoIrpf: descontoIrpf,
            salarioLiquido: salarioLiquido,
        };
    }

    _calculaDescontoInss = (baseInss) => {
        let descontoInss = 0;
        for (let item of tabelaINSS_2018) {
            if (baseInss <= item.ate) {
                descontoInss = Math.min(baseInss * (item.aliquota / 100), TETO_INSS_2018);
                break;
            }
        }
        return descontoInss;
    }

    _calculaDescontoIrpf = (baseIrpf) => {
        let descontoIRPF = 0;

        for (let item of tabelaIRPF_2018) {
            if (baseIrpf <= item.ate) {
                descontoIRPF = baseIrpf * (item.aliquota / 100);
                descontoIRPF -= item.deducao;
                break;
            }
        }

        return descontoIRPF;
    }

    _calcularSalarioBruto = (event) => {        
        var salarioLiquidoCalular = parseInt(document.getElementById('salarioLiquidoCalular').value);        
        if (salarioLiquidoCalular) {
            let salarioCalculado = this._calculaSalario(salarioLiquidoCalular);                                    
            
            let i = 0; 
            let incremento = 1;           
            let intervalId = setInterval(() => {                
                i += incremento;
                let salario = this._calculaSalario(salarioCalculado.salario + (i));  
                this.setState(salario);    
                if(salarioLiquidoCalular - salario.salarioLiquido > 300){
                    incremento = 10;
                }
                if(salarioLiquidoCalular - salario.salarioLiquido < 5){
                    incremento = 0.10;
                }
                if(salario.salarioLiquido >= salarioLiquidoCalular)
                    clearInterval(intervalId);
            }, 1)
            

        }
    }

    render() {
        return (
            <div>
                <FormControl fullWidth className={styles.margin}>
                    <InputLabel htmlFor="salario">Salário</InputLabel>
                    <Input
                        id="salario"
                        value={this.state.salario}
                        onChange={this._onSalarioChange}
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    />
                </FormControl>
                <FormControl fullWidth className={styles.margin} disabled>
                    <InputLabel htmlFor="baseInss">Base INSS:</InputLabel>
                    <Input
                        id="baseInss"
                        value={this.state.baseInss}
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    />
                </FormControl>
                <FormControl fullWidth className={styles.margin} disabled>
                    <InputLabel htmlFor="descontoInss">Desconto INSS:</InputLabel>
                    <Input
                        id="descontoInss"
                        value={this.state.descontoInss}
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    />
                </FormControl>
                <FormControl fullWidth className={styles.margin} disabled>
                    <InputLabel htmlFor="baseIrpf">Base IRPF:</InputLabel>
                    <Input
                        id="baseIrpf"
                        value={this.state.baseIrpf}
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    />
                </FormControl>
                <FormControl fullWidth className={styles.margin} disabled>
                    <InputLabel htmlFor="descontoIrpf">Desconto IRPF:</InputLabel>
                    <Input
                        id="descontoIrpf"
                        value={this.state.descontoIrpf}
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    />
                </FormControl>
                <FormControl fullWidth className={styles.margin} disabled>
                    <InputLabel htmlFor="salarioLiquido">Salário líquido:</InputLabel>
                    <Input
                        id="salarioLiquido"
                        value={this.state.salarioLiquido}                        
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    />
                </FormControl>
                <FormControl fullWidth className={styles.margin} >
                    <InputLabel htmlFor="salarioLiquidoCalular">Salário líquido(Calculo):</InputLabel>
                    <Input
                        id="salarioLiquidoCalular"                                      
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    />
                    <Button variant="outlined" color="primary" onClick={this._calcularSalarioBruto}>
                        Calcular
                    </Button>
                </FormControl>

            </div>
        )
    }
}