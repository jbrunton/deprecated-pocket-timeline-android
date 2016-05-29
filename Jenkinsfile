node {
    stage 'Checkout'
    checkout scm

    // trying out making a plugin
    step([$class: 'HelloWorldBuilder', name: 'John'])

    echo "BRANCH_NAME: $env.BRANCH_NAME"
    echo "PR_BASE_REF: $env.PR_BASE_REF"

//    def pulls = httpRequest "https://api.github.com/repos/jbrunton/pocket-timeline-android/pulls"
//    echo pulls
//
//    stage 'Build'
//    sh './gradlew assembleDebug'
//    step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])
//
//    stage 'Sonar'
//    sh "./gradlew sonarqube -Dsonar.branch=$env.BRANCH_NAME"
//
//    stage 'Test'
//    sh './gradlew testAll'
}