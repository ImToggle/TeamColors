plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.3"

stonecutter {
    tasks {
        order("publishModrinth")
    }

    parameters {
        replacements {
            string(eval(current.version, "= 1.8.9")) {
             
            }
            string(current.parsed < "26.1") {
                replace("classTweaker v1 official", "classTweaker v1 named")
            }
        }
    }
}