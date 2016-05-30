node {
    stage 'Checkout'
    checkout scm

//    sh 'env'

    def pr = pullRequestProperties(
            branchName: env.BRANCH_NAME,
            repository: 'jbrunton/pocket-timeline-android'
    );
    def targetBranch = pr.targetBranch
    def commitSha = pr.commitSha

    echo "commitSha: $commitSha"

//    stage 'Build'
//    sh './gradlew assembleDebug'
//    step([$class: 'ArtifactArchiver', artifacts: '**/apk/app-debug.apk', fingerprint: true])
//
//    stage 'Sonar'
//    // first run sonar against the target branch...
//    sh "git checkout $targetBranch"
//    sh "./gradlew sonarqube -Dsonar.buildbreaker.skip=true -Dsonar.branch=$env.BRANCH_NAME"
//    // ...then run against with our PR merged to compare
//    sh "git checkout $commitSha"
//    sh "./gradlew sonarqube -Dsonar.branch=$env.BRANCH_NAME"
//
//    stage 'Test'
//    sh './gradlew testAll'
}