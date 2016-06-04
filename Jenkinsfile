node {
    stage 'Checkout'
    checkout scm

    stage 'Build'
    //sh './gradlew assembleDebug'
    //step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])

    if (env.BRANCH_NAME == 'master') {
        stage 'Sonar'
        sh './gradlew sonarqube -Dsonar.buildbreaker.skip=true'
    }

    stage 'Test'
    //sh './gradlew testAll'

    if (env.CHANGE_ID != null) {
        def mergeRef = "origin/pr/$env.CHANGE_ID"

        stage 'Checkout (merge)'
        sh "git checkout $mergeRef"

        stage 'Build (merge)'
        //sh './gradlew assembleDebug'
        //step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])

        stage 'Sonar (merge)'
        // first run sonar against the target branch...
        sh "git checkout $env.CHANGE_TARGET"
        sh "./gradlew sonarqube \
            -Dsonar.buildbreaker.skip=true \
            -Dsonar.branch=$env.CHANGE_TARGET"
        // ...then run against with our PR merged to compare
        sh "git checkout $mergeRef"

        withCredentials([[$class: 'StringBinding',
                          credentialsId: 'jbrunton-minion-ci-access-token',
                          variable: 'ACCESS_TOKEN']]) {
            sh "./gradlew sonarqube \
                -Dsonar.github.repository=jbrunton/pocket-timeline-android \
                -Dsonar.github.pullRequest=$env.CHANGE_ID \
                -Dsonar.github.oauth=$env.ACCESS_TOKEN \
                -Dsonar.analysis.mode=preview \
                -Dsonar.branch=$env.CHANGE_TARGET"
        }

        stage 'Test (merge)'
        //sh './gradlew testAll'
    }
}
