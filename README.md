# 🍔 FoodOrderingSystem

Sistema de pedidos de comida utilizando **Spring Boot**, **PostgreSQL**, **RabbitMQ** e **Docker**.

---

## 🐳 Rodando com Docker

### 📦 Requisitos
- [Docker](https://www.docker.com/)
---

### 🚀 Subindo os containers

No terminal, execute:

```bash
docker compose up --build
```

Isso irá subir os seguintes serviços:

- 📦 Aplicação Spring Boot
- 🐘 PostgreSQL com o banco `FoodOrderingSystem`
- 🐇 RabbitMQ (mensageria)
- 🧑‍💼 pgAdmin (gerenciador de banco)

---

## 🌐 Acessos úteis

| Serviço    | URL                                              | Login                                      |
| ---------- | ------------------------------------------------ | ------------------------------------------ |
| 🖥️ API     | [http://localhost:8080](http://localhost:8080)   | —                                          |
| 🧑‍💼 pgAdmin | [http://localhost:5050](http://localhost:5050)   | Email: `admin@admin.com`<br>Senha: `admin` |
| 🐇 RabbitMQ | [http://localhost:15672](http://localhost:15672) | Usuário: `myuser`<br>Senha: `secret`       |

---

## 🧪 Banco de Dados

O banco é inicializado automaticamente com:

- **Estrutura de tabela e dados iniciais:** populados via script `./database/init.sql` no primeiro start do container

Credenciais padrão:

```bash
POSTGRES_DB=FoodOrderingSystem
POSTGRES_USER=postgres
POSTGRES_PASSWORD=admin
```

---

## 📡 Exemplo de requisições

### Criar pedido

```bash
curl -X POST http://localhost:8080/orders   -H "Content-Type: application/json"   -d '{
    "customerGuid": "b29c3d4f-77e0-4b41-9e7d-3c1a1f6de1ef",
    "restaurantGuid": "33fa4d6d-d3e5-43f5-9273-cb67f8077ea3",
    "items": [
      { "itemGuid": "6b9d5343-22c3-4ad5-a1d3-1df9a5190f7a", "quantity": 1 },
      { "itemGuid": "0e29e84b-e220-4f87-a4f3-e5cd84d09ee7", "quantity": 1 }
    ],
    "paymentTypeGuid": "160e72be-ff7e-4ddb-89fa-fb1b4ce5e71c"
  }'
```

### Consultar pedidos

```bash
curl -X GET http://localhost:8080/orders
```

### Confirmar pedido

```bash
curl -X POST http://localhost:8080/orders/confirmOrder   -H "Content-Type: application/json"   -d '{
    "orderId": "GUID_DO_PEDIDO"
  }'
```

Substitua `"GUID_DO_PEDIDO"` pelo `guid` real do pedido, por exemplo:

```json
"orderId": "f2be14ff-5b6d-4cc2-bb2b-cf0a2b4c170f"
```

---

## 🧼 Parar os containers

```bash
docker compose down
```

Para apagar tudo (incluindo os volumes do banco):

```bash
docker compose down -v
```

---

🛠️ _Desenvolvido por Leticia Takenaka_
