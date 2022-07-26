pipeline {
    agent any;

    stages {
        stage("Build") {
            steps {
                echo 'Building...'
                sh """
                    mvn clean install -DskipTests=true
                """
            }
        }
        stage("Test") {
            steps {
                echo 'Testing...'
                sh """
                    mvn test -Dsurefire.useFile=false
                """
            }
        }
    }
}
