def call() {
  node() {
    common.codeCheckOut()
    common.sonarCheck()
  }
}
