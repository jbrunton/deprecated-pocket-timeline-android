node {
    stage 'Checkout'
    checkout scm
    def pr = pullRequestProperties(
            branchName: env.BRANCH_NAME,
            repository: 'jbrunton/pocket-timeline-android'
    )
    sh "git checkout $pr.mergeRef"

    stage 'Build'
    sh './gradlew assembleDebug'
    step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])

    stage 'Sonar'
    echo "Comparing $pr.sourceBranch with $pr.targetBranch"
    // first run sonar against the target branch...
    sh "git checkout $pr.targetBranchRef"
    sh "./gradlew sonarqube -Dsonar.buildbreaker.skip=true -Dsonar.branch=$pr.sourceBranch"
    // ...then run against with our PR merged to compare
    sh "git checkout $pr.mergeRef"
    sh "./gradlew sonarqube -Dsonar.branch=$pr.sourceBranch"

    stage 'Test'
    sh './gradlew testAll'
}