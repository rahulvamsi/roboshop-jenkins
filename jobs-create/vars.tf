variable "folders" {
  default = ["infrastructure"]
}

variable "infra-jobs" {
  default = ["terraform-mutable-infra", "cart-infra", "catalogue-infra", "user-infra", "payment-infra", "shipping-infra"]
}
