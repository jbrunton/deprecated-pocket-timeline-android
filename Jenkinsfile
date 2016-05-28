node {
    stage 'Checkout'
    checkout scm

    stage 'Build'
    sh './gradlew assembleDebug'
    step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])

    stage 'Sonar'
    sh "./gradlew sonarqube -Dsonar.branch=$env.BRANCH_NAME"

    stage 'Test'
    sh './gradlew testAll'
}