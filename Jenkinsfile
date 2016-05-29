node {
    stage 'Checkout'
    checkout scm

    def prProperties = pullRequestProperties();
    def sourceBranch = prProperties['sourceBranch'];
    def targetBranch = prProperties['targetBranch'];
    def sourceBranchSha = prProperties['sourceBranchSha'];
    def targetBranchSha = prProperties['targetBranchSha'];
    echo "sourceBranch: $sourceBranch, sha: $sourceBranchSha"
    echo "targetBranch: $targetBranch, sha: $targetBranchSha"
//
//    // trying out making a plugin
//    def workspace = pwd();
//    def propertiesFilePath = "$workspace/pr_env.props"
//    echo "propertiesFilePath: $propertiesFilePath"
//    step([$class: 'HelloWorldBuilder', propertiesFilePath: propertiesFilePath])
//    step([$class: 'org.jenkinsci.plugins.envinject.EnvInjectBuilder', propertiesFilePath: propertiesFilePath, propertiesContent: readFile('pr_env.props')])
//
//    echo "BRANCH_NAME: $env.BRANCH_NAME"
//    echo "PR_BASE_REF: $env.PR_BASE_REF"

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