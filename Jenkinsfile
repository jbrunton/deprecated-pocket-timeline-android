node {
    stage 'Checkout'
    checkout scm

    stage 'Environment'
    sh 'env'

//    stage 'Build'
//    sh './gradlew assembleDebug'
//    step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])
//
//    if (env.BRANCH_NAME == 'master') {
//        stage 'Sonar'
//        sh './gradlew sonarqube -Dsonar.buildbreaker.skip=true'
//    }
//
//    stage 'Test'
//    sh './gradlew testAll'
//
//    if (env.BRANCH_NAME != 'master') {
//        stage 'Checkout (merge)'
//        def pr = pullRequestProperties(
//                branchName: env.BRANCH_NAME,
//                repository: 'jbrunton/pocket-timeline-android'
//        )
//        echo "Found pull request from $pr.sourceBranch to $pr.targetBranch. Checking out $pr.mergeRef"
//        sh "git checkout $pr.mergeRef"
//
//        stage 'Build (merge)'
//        sh './gradlew assembleDebug'
//        step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])
//
//        stage 'Sonar (merge)'
//        echo "Comparing $pr.sourceBranch with $pr.targetBranch"
//        // first run sonar against the target branch...
//        sh "git checkout $pr.targetBranchRef"
//        sh "./gradlew sonarqube -Dsonar.buildbreaker.skip=true -Dsonar.branch=$pr.sourceBranch"
//        // ...then run against with our PR merged to compare
//        sh "git checkout $pr.mergeRef"
//        sh "./gradlew sonarqube -Dsonar.branch=$pr.sourceBranch"
//
//        stage 'Test (merge)'
//        sh './gradlew testAll'
//    }
}
