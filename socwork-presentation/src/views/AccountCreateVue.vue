<script>
    export default {
        data() {
            return {
                formData: {
                    username: "",
                    password: ""
                }
            }
        },
        methods: {
            async submit() {
                const options = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.formData)
                };
                const response = await fetch('http://localhost:8080/accounts', options);
                if(response.ok) {
                    alert('Compte créé avec succès');
                } else {
                    console.error('Dev is a failure!');
                }
            },

        }
    }
</script>

<template>
    <main>
        <h1>Créer un compte</h1>
        <form @submit.prevent="submit" novalidate>
            <div>
                <label for="username">Nom d'utilisateur</label>
                <input v-model="formData.username" type="text" name="username" id="username" placeholder="email"/>
            </div>
            <div>
                <label for="password">Password</label>
                <input v-model="formData.password" type="password" name="password" id="password" />
            </div>
            <input type="submit" value="Créer le compte" />
        </form>
    </main>
</template>

<style scoped>
    h1 {
        color: blue;
    }
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
