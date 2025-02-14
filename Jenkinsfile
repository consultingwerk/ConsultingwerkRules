pipeline {
  agent { label 'Linux-Office03' }
  options {
    buildDiscarder(logRotator(numToKeepStr:'10'))
    timeout(time: 15, unit: 'MINUTES')
    skipDefaultCheckout()
  }

  stages {
    stage ('Build OpenEdge rules') {
      steps {
        checkout([$class: 'GitSCM', branches: scm.branches, extensions: scm.extensions + [[$class: 'CleanCheckout']], userRemoteConfigs: scm.userRemoteConfigs])
        script {
          withEnv(["PATH+MAVEN=${tool name: 'Maven 3', type: 'hudson.tasks.Maven$MavenInstallation'}/bin", "JAVA_HOME=${tool name: 'JDK17', type: 'jdk'}"]) {
            if (("main" == env.BRANCH_NAME) || ("develop" == env.BRANCH_NAME) || env.BRANCH_NAME.startsWith("release") || env.BRANCH_NAME.startsWith("hotfix")) {
              sh "git rev-parse HEAD > current-commit"
              def currCommit = readFile('current-commit').replace("\n", "").replace("\r", "")
              sh "mvn clean install -Dmaven.test.failure.ignore=true -Dgit.commit=${currCommit}"
            } else {
              sh "mvn clean package -Dmaven.test.failure.ignore=true"
            }
          }
        }
        archiveArtifacts artifacts: 'target/*.jar'
        step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
      }
    }

  }
}
