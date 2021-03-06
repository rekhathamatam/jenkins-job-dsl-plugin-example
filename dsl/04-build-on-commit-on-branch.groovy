/*
 *  1. Make a couple of commits on `failing_tests` branch to see that job is run after change in repo
 *  (it checks for changes every minute)
 *
 */

import config.*

def String projectDir = 'projects/vending-machine-kata-solution'

job('04-build-on-commit-on-branch') {
    scm {
        git {
            remote {
                url(ThisRepository.localGitUrl())
            }
            branch('*/failing_tests')
        }
    }
    triggers {
        scm(CronExpressions.everyMinute)
    }
    jdk(Environment.jdkInstallation)
    steps {
        maven {
            mavenInstallation(Environment.mavenInstallation)
            rootPOM("${projectDir}/pom.xml")
            goals('test')
        }
    }
}

