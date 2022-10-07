provider "jenkins" {
  server_url = "http://172.31.0.150:8080"
  username   = "admin"
  password   = "admin123"
}

terraform {
  required_providers {
    jenkins = {
      source = "registry.terraform.io/taiidani/jenkins"
    }
  }
}

resource "jenkins_folder" "folder" {
  count       = length(var.folders)
  name        = element(var.folders, count.index)
  description = element(var.folders, count.index)
}

