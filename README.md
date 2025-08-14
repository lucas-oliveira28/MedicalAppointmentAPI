# MedicalAppointmentAPI

API REST para gerenciamento de consultas médicas (pacientes, médicos, disponibilidades e agendamentos), construída com Spring Boot 3 e banco H2 em memória para desenvolvimento/testes.

## Sumário
- Visão geral
- Tecnologias
- Requisitos
- Como executar
- Banco de dados (H2 Console)
- Endpoints da API
  - Pacientes
  - Médicos
  - Disponibilidades de Médicos
  - Consultas (Appointments)
- Padrão de erros
- Formatos e convenções
- Execução de testes
- Licença

## Visão geral
Esta API permite:
- Cadastrar e gerenciar Pacientes e Médicos;
- Definir disponibilidades de atendimento dos Médicos;
- Agendar, consultar, atualizar e cancelar Consultas.

O projeto já vem configurado com validações de entrada, tratamento de erros e um banco H2 em memória para facilitar a execução local.

## Tecnologias
- Java 21
- Spring Boot 3.5.x (Web, Validation, Data JPA)
- H2 Database (perfil de teste)
- Maven

## Requisitos
- JDK 21 instalado
- Maven 3.9+ (opcional — o projeto inclui Maven Wrapper: mvnw/mvnw.cmd)

## Como executar
1. Clonar o repositório
2. Na raiz do projeto, executar (Windows):
   - mvnw.cmd spring-boot:run
   
   Em Unix/macOS:
   - ./mvnw spring-boot:run

Por padrão, o perfil ativo é test (application.yml), que usa H2 em memória. A aplicação subirá em http://localhost:8080.

## Banco de dados (H2 Console)
- Console H2 habilitado em: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- User: sa
- Password: (vazio)

## Endpoints da API
Abaixo um resumo dos principais endpoints. Os corpos de requisição/resposta são JSON.

Observação importante sobre datas:
- DateTime (consulta e disponibilidade): formato "dd-MM-yyyy'T'HH:mm" (ex.: 25-12-2025'T'14:30)
- Date (data de nascimento): formato "dd-MM-yyyy"

### Pacientes
Base path: /patients

- GET /patients
  - Lista todos os pacientes.
  - 200 OK

- GET /patients/search?name={nome}
- GET /patients/search?cpf={cpf}
  - Busca por nome OU cpf.
  - 200 OK se encontrado; 404 se não encontrado.

- POST /patients/new
  - Cria um paciente.
  - Body (exemplo):
    {
      "name": "João Silva",
      "email": "joao.silva@example.com",
      "phone": "(11) 91234-5678",
      "cpf": "123.456.789-09",
      "address": "Rua A, 123",
      "birthDate": "01-01-1990",
      "gender": "MALE",
      "password": "senhaSegura123",
      "medicalHistory": "Sem alergias"
    }
  - 201 Created

- PUT /patients/update/{id}
  - Atualiza dados de um paciente. Os campos são opcionais; apenas os enviados serão atualizados.
  - Body (exemplo parcial):
    {
      "email": "novo.email@example.com",
      "phone": "11 91234-5678",
      "active": true
    }
  - 200 OK; 404 se não encontrado.

- DELETE /patients/delete/{id}
  - Remove um paciente.
  - 204 No Content (o serviço retorna um corpo com o recurso removido, apesar do status 204).

Campos/Enums relevantes:
- gender: MALE, FEMALE, NON_BINARY, OTHER

### Médicos
Base path: /doctors

- GET /doctors
  - Lista todos os médicos. 200 OK

- GET /doctors/search?name={nome}
- GET /doctors/search?crm={CRM}
  - Busca por nome OU CRM (formato do CRM: 5 dígitos, hífen e UF, ex.: 12345-SP). 200/404

- POST /doctors/new
  - Body (exemplo):
    {
      "name": "Dra. Maria Souza",
      "email": "maria.souza@example.com",
      "phone": "(11) 98765-4321",
      "crm": "12345-SP",
      "password": "senhaForte456",
      "specialty": "CARDIOLOGY"
    }
  - 201 Created

- PUT /doctors/update/{id}
  - Atualização parcial semelhante a Pacientes. 200/404

- DELETE /doctors/delete/{id}
  - Remove um médico. 204 No Content (com corpo retornado pelo serviço).

Enums de especialidade (exemplos):
- CARDIOLOGY, DERMATOLOGY, PEDIATRICS, GYNECOLOGY, ORTHOPEDICS, NEUROLOGY, PSYCHIATRY, ...
  (ver lista completa em src/main/java/.../entities/enums/MedicalSpecialty.java)

### Disponibilidades de Médicos
Base path: /doctoravailability

- GET /doctoravailability/available
  - Lista janelas disponíveis (não agendadas). 200 OK

- GET /doctoravailability/scheduled
  - Lista janelas já agendadas (indisponíveis). 200 OK

- POST /doctoravailability/new
  - Cria uma janela de disponibilidade.
  - Body (exemplo):
    {
      "doctorId": 1,
      "startDate": "20-12-2025'T'09:00",
      "endDate": "20-12-2025'T'10:00"
    }
  - 201 Created

- PUT /doctoravailability/update/{id}
  - Atualiza disponibilidade (parcial).
  - Body (exemplo):
    {
      "available": false,
      "startDate": "20-12-2025'T'09:30",
      "endDate": "20-12-2025'T'10:30"
    }
  - 200 OK; 404 se não encontrado.

### Consultas (Appointments)
Base path: /appointments

- GET /appointments
  - Lista todas as consultas. 200 OK

- GET /appointments/search?id={id}
- GET /appointments/search?doctorId={doctorId}
- GET /appointments/search?patientId={patientId}
  - Busca por um dos critérios acima. 200/404

- POST /appointments/new
  - Agenda uma consulta (também marca a disponibilidade como indisponível).
  - Body (exemplo):
    {
      "date": "22-12-2025'T'14:00",
      "reason": "Retorno de exame",
      "status": "SCHEDULED",
      "patientId": 1,
      "doctorId": 2,
      "availabilityId": 10
    }
  - 201 Created

- PUT /appointments/update/{id}
  - Atualiza campos da consulta. Para cancelar, envie status = "CANCELLED" e reasonForCancellation.
  - Body (exemplo de cancelamento):
    {
      "status": "CANCELLED",
      "reasonForCancellation": "Paciente indisponível"
    }
  - 200 OK; 404 se não encontrado.

- DELETE /appointments/delete/{id}
  - Remove a consulta e reabre a disponibilidade vinculada. 204 No Content (com corpo retornado pelo serviço).

Enums de status da consulta:
- SCHEDULED, CANCELLED, FINISHED

## Padrão de erros
A API utiliza um formato padrão para erros (StandardError):
{
  "instant": "2025-01-01T10:00:00Z",
  "status": 400,
  "error": "Bad request error",
  "message": "campoX: mensagem de erro, campoY: mensagem de erro",
  "path": "/endpoint"
}

Principais códigos de status:
- 200 OK, 201 Created, 204 No Content
- 400 Bad Request (validações)
- 404 Not Found (recurso não encontrado)

## Formatos e convenções
- Telefone aceito: "(DD) 9XXXX-XXXX" ou "DD 9XXXX-XXXX" (o sistema normaliza internamente)
- CPF válido e único para pacientes
- CRM no formato "99999-UF" (único) para médicos
- As atualizações via PUT aceitam corpo parcial: apenas campos não nulos são aplicados

## Execução de testes
- mvnw.cmd test (Windows) ou ./mvnw test (Unix/macOS)

## Licença
Este projeto está sob a licença MIT. Consulte o arquivo LICENSE na raiz do repositório.