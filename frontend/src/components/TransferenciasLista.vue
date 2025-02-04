<template>
  <div class="container">
    <h1>💸 Agendamento de Transferências</h1>

    <div class="card">
      <h2>Nova Transferência</h2>
      <form @submit.prevent="agendarTransferencia">
        <div class="input-group">
          <label>Conta de Origem</label>
          <input v-model="contaOrigem" placeholder="Ex: 1234567890" required />
        </div>

        <div class="input-group">
          <label>Conta de Destino</label>
          <input v-model="contaDestino" placeholder="Ex: 0987654321" required />
        </div>

        <div class="input-group">
          <label>Valor (R$)</label>
          <input v-model="valor" type="number" placeholder="Ex: 1000" required />
        </div>

        <div class="input-group">
          <label>Data da Transferência</label>
          <input v-model="dataTransferencia" type="date" required />
        </div>

        <button type="submit" class="btn">📩 Agendar Transferência</button>
      </form>

      <p v-if="erro" class="error-message">{{ erro }}</p>
      <p v-if="sucesso" class="success-message">{{ sucesso }}</p>
    </div>

    <h2>📜 Extrato de Transferências</h2>
    <table v-if="transferencias.length > 0">
      <thead>
        <tr>
          <th>Origem</th>
          <th>Destino</th>
          <th>Valor</th>
          <th>Taxa</th>
          <th>Data Agendamento</th>
          <th>Data Transferência</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="t in transferencias" :key="t.id">
          <td>{{ t.contaOrigem }}</td>
          <td>{{ t.contaDestino }}</td>
          <td>R$ {{ formatarMoeda(t.valor) }}</td>
          <td>R$ {{ formatarMoeda(t.taxa) }}</td>
          <td>{{ formatarData(t.dataAgendamento) }}</td>
          <td>{{ formatarData(t.dataTransferencia) }}</td>
        </tr>
      </tbody>
    </table>
    <p v-else class="no-data">Nenhuma transferência cadastrada.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      contaOrigem: '',
      contaDestino: '',
      valor: '',
      dataTransferencia: '',
      transferencias: [],
      erro: '',
      sucesso: ''
    };
  },
  methods: {
    async agendarTransferencia() {
      this.erro = '';
      this.sucesso = '';
      try {
        await axios.post('http://localhost:8080/api/transferencias', {
          contaOrigem: this.contaOrigem,
          contaDestino: this.contaDestino,
          valor: this.valor,
          dataTransferencia: this.dataTransferencia,
          dataAgendamento: new Date().toISOString().split('T')[0]
        });
        this.sucesso = "✅ Transferência agendada com sucesso!";
        this.carregarTransferencias();
      } catch (error) {
        this.erro = "❌ Erro ao agendar a transferência: " + (error.response?.data || "Verifique os dados e tente novamente.");
      }
    },
    async carregarTransferencias() {
      try {
        const response = await axios.get('http://localhost:8080/api/transferencias');
        this.transferencias = response.data;
      } catch (error) {
        this.erro = "Erro ao carregar transferências.";
      }
    },
    formatarData(data) {
      return new Date(data).toLocaleDateString('pt-BR');
    },
    formatarMoeda(valor) {
      return parseFloat(valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    }
  },
  mounted() {
    this.carregarTransferencias();
  }
};
</script>

<style>
.container {
  max-width: 800px;
  margin: auto;
  text-align: center;
  font-family: Arial, sans-serif;
}

.card {
  background: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.input-group {
  margin-bottom: 10px;
  text-align: left;
}

.input-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

.input-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.btn {
  background-color: #28a745;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.btn:hover {
  background-color: #218838;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

table th, table td {
  border: 1px solid #ddd;
  padding: 8px;
}

table th {
  background-color: #007bff;
  color: white;
}

.no-data {
  color: #666;
  font-style: italic;
}

.error-message {
  color: red;
  font-weight: bold;
}

.success-message {
  color: green;
  font-weight: bold;
}
</style>