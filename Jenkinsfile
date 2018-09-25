#!groovy

stage 'Build OpenEdge rules'
node ('master') {
  checkout scm
  withEnv(["PATH+MAVEN=${tool name: 'Maven 3', type: 'hudson.tasks.Maven$MavenInstallation'}/bin"]) {
    sh "git rev-parse HEAD > current-commit"
    def currCommit = readFile('current-commit').replace("\n", "").replace("\r", "")
    sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true -Dgit.commit=${currCommit}"
  }
  archiveArtifacts artifacts: 'target/*-plugin-*.jar'
  step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])

  if ("master" == env.BRANCH_NAME) {
    withEnv(["PATH+MAVEN=${tool name: 'Maven 3', type: 'hudson.tasks.Maven$MavenInstallation'}/bin"]) {
      withCredentials([[$class: 'StringBinding', credentialsId: 'AdminTokenSonarQube', variable: 'SQ_TOKEN']]) {
        sh "mvn -Dsonar.host.url=http://sonar.riverside-software.fr -Dsonar.login=${env.SQ_TOKEN} sonar:sonar"
      }
    }
  } else {
    withEnv(["PATH+MAVEN=${tool name: 'Maven 3', type: 'hudson.tasks.Maven$MavenInstallation'}/bin"]) {
      withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'GitHub-GQuerret', usernameVariable: 'GH_LOGIN', passwordVariable: 'GH_PASSWORD'], [$class: 'StringBinding', credentialsId: 'AdminTokenSonarQube', variable: 'SQ_TOKEN']]) {
        sh "mvn -Dsonar.host.url=http://sonar.riverside-software.fr -Dsonar.login=${env.SQ_TOKEN} -Dsonar.analysis.mode=issues -Dsonar.github.pullRequest=${env.BRANCH_NAME.substring(3)} -Dsonar.github.repository=consultingwerk/ConsultingwerkRules -Dsonar.github.oauth=${env.GH_PASSWORD} sonar:sonar"
      }
    }
  }
}

