const form = document.getElementById("employeeForm");

const nameInput =
    document.getElementById("name");

const ageInput =
    document.getElementById("age");

const experienceInput =
    document.getElementById("experience");

const departmentInput =
    document.getElementById("department");

const stateInput =
    document.getElementById("state");



/*
 * Show Error
 */

function showError(input, errorId, message) {

    input.classList.add("input-error");

    input.classList.remove("input-success");

    document.getElementById(errorId).textContent =
        message;
}



/*
 * Show Success
 */

function showSuccess(input, errorId) {

    input.classList.remove("input-error");

    input.classList.add("input-success");

    document.getElementById(errorId).textContent =
        "";
}



/*
 * Validate Name
 */

function validateName() {

    const name =
        nameInput.value.trim();

    const pattern =
        /^[A-Za-z ]+$/;


    if (name === "") {

        showError(
            nameInput,
            "nameError",
            "Employee name is required."
        );

        return false;
    }


    if (name.length < 2) {

        showError(
            nameInput,
            "nameError",
            "Name must contain at least 2 characters."
        );

        return false;
    }


    if (!pattern.test(name)) {

        showError(
            nameInput,
            "nameError",
            "Name should contain only letters and spaces."
        );

        return false;
    }


    showSuccess(
        nameInput,
        "nameError"
    );

    return true;
}



/*
 * Validate Age
 */

function validateAge() {

    const value =
        ageInput.value.trim();

    const age =
        Number(value);


    if (value === "") {

        showError(
            ageInput,
            "ageError",
            "Age is required."
        );

        return false;
    }


    if (
        !Number.isInteger(age) ||
        age < 18 ||
        age > 65
    ) {

        showError(
            ageInput,
            "ageError",
            "Age must be between 18 and 65."
        );

        return false;
    }


    showSuccess(
        ageInput,
        "ageError"
    );

    return true;
}



/*
 * Validate Experience
 */

function validateExperience() {

    const value =
        experienceInput.value.trim();

    const experience =
        Number(value);


    if (value === "") {

        showError(
            experienceInput,
            "experienceError",
            "Experience is required."
        );

        return false;
    }


    if (
        Number.isNaN(experience) ||
        experience < 0 ||
        experience > 40
    ) {

        showError(
            experienceInput,
            "experienceError",
            "Experience must be between 0 and 40 years."
        );

        return false;
    }


    showSuccess(
        experienceInput,
        "experienceError"
    );

    return true;
}



/*
 * Validate Department
 */

function validateDepartment() {

    if (departmentInput.value === "") {

        showError(
            departmentInput,
            "departmentError",
            "Please select a department."
        );

        return false;
    }


    showSuccess(
        departmentInput,
        "departmentError"
    );

    return true;
}



/*
 * Validate State
 */

function validateState() {

    if (stateInput.value === "") {

        showError(
            stateInput,
            "stateError",
            "Please select a state."
        );

        return false;
    }


    showSuccess(
        stateInput,
        "stateError"
    );

    return true;
}



/*
 * Live Validation
 */

nameInput.addEventListener(
    "input",
    validateName
);

ageInput.addEventListener(
    "input",
    validateAge
);

experienceInput.addEventListener(
    "input",
    validateExperience
);

departmentInput.addEventListener(
    "change",
    validateDepartment
);

stateInput.addEventListener(
    "change",
    validateState
);



/*
 * Submit Validation
 */

form.addEventListener(
    "submit",
    function (event) {

        const nameValid =
            validateName();

        const ageValid =
            validateAge();

        const experienceValid =
            validateExperience();

        const departmentValid =
            validateDepartment();

        const stateValid =
            validateState();


        /*
         * Stop form if validation fails
         */

        if (
            !nameValid ||
            !ageValid ||
            !experienceValid ||
            !departmentValid ||
            !stateValid
        ) {

            event.preventDefault();

            alert(
                "Please correct the errors before submitting."
            );

            return;
        }


        /*
         * IMPORTANT:
         *
         * Do NOT use event.preventDefault()
         * here.
         *
         * Browser submits normally to:
         *
         * EmployeeServlet
         */

    }
);



/*
 * Clear Form
 */

function clearForm() {

    form.reset();


    const inputs =
        form.querySelectorAll(
            "input, select"
        );


    inputs.forEach(
        function (input) {

            input.classList.remove(
                "input-error",
                "input-success"
            );

        }
    );


    const errors =
        form.querySelectorAll(
            ".error"
        );


    errors.forEach(
        function (error) {

            error.textContent = "";

        }
    );


    /*
     * Country should remain India
     */

    document.getElementById("country").value =
        "India";
}



/*
 * Refresh Page
 */

function refreshPage() {

    window.location.reload();

}