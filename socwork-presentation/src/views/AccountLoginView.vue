<script>
    export default {
        data() {
            return {
                inputs: {
                    username: "",
                    password: ""
                },
                toto: {
                    OK: "Toto get !",
                    KO: "Toto not gotten :(",
                    displayedText: ""
                },
                token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MzIyODk2MDMsInN1YiI6ImFiYyIsImV4cCI6MTc2MzgyMTI3OX0.eJekWrEVeBs_h77WB0tIZzjj-EKT4vbMKw-ODWo23lg"
            }
        },
        methods: {
            async submit() {
                const options = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.inputs)
                };
                const response = await fetch('http://localhost:8080/accounts/login', options);
                if(response.status == 201) {
                    alert('Good Credentials');
                    this.token = await response.body.json();
                    console.log(this.token)
                } else if(response.status == 401) {
                    alert('Bad Credentials');
                } else {
                    console.error('Dev is a failure!');
                }
            },
            async getToto() {
                const options = {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${this.token}`,
                        'Content-Type': 'application/json'
                    }
                };
                const response = await fetch('http://localhost:8080/accounts/toto', options);
                if(response.ok) {
                    this.toto.displayedText = this.toto.OK;
                } else {
                    this.toto.displayedText = this.toto.KO;
                }
            }
        }
    }
</script>

<template>
    <main>
        <h1>Login</h1>
        <form @submit.prevent="submit" novalidate>
            <div>
                <label for="username">Nom d'utilisateur</label>
                <input v-model="inputs.username" type="text" name="username" id="username" placeholder="email"/>
            </div>
            <div>
                <label for="password">Password</label>
                <input v-model="inputs.password" type="password" name="password" id="password" />
            </div>
            <input type="submit" value="Login" />
        </form>
        <button @click="getToto">get toto</button>
        {{ this.toto.displayedText }}
    </main>
</template>

<style scoped>
    div {
        display: flex;
        flex-direction: column;
        width: fit-content;
    }

    form > div {
        position: relative;
        margin: 1rem;
    }

    label::after {
        content: ' *';
        color: red;
    }
</style>
