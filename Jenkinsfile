node {
    stage 'Checkout'
    checkout scm

    stage 'Build'
    sh './gradlew assembleDebug'
    step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])

//    stage 'Sonar'
//    // no point using build breaker on master - should never break, but if it does
//    // then either something went wrong elsewhere or we're expediting an urgent fix
//    sh './gradlew sonarqube -Dsonar.buildbreaker.skip=false'
//
//    stage 'Test'
//    sh './gradlew testAll'
}