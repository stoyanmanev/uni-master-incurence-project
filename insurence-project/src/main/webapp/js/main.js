// global variables

let timeout;

function aways(){

    window.addEventListener('load', siteLoader);
    window.addEventListener('resize', fixed());

    if(document.querySelector('#login-form')){
        onLoginForm(document.querySelector('#login-form'));
    }

    if(document.querySelector('#register-form')){
        onRegisterForm(document.querySelector('#register-form'));
    }

    if(document.querySelector('#create-client-form')){
        onCreateCustomer(document.querySelector('#create-client-form'));
    }
    if(document.querySelector('#create-insurence-form')){
        onCreateInsurence(document.querySelector('#create-insurence-form'));
    }

    if(document.querySelector('#create-contract-form')){
        loadDataClient();
        loadDataInsurence();
        onCreateContract(document.querySelector('#create-contract-form'));
    }

    if(window.location.pathname !== '/index.html'&& window.location.pathname !== '/pages/error.html' && window.location.pathname !== '/'){
        onExistUser();
    }

    if(window.location.pathname.includes('admin')){
        credentials(); // todo 
    }

    if(document.querySelector('#table')){
        credentials(true);
    }

    if(window.location.pathname.includes('/customer/index.html')){
        onLoadTableInfo("/customer-table");
        document.querySelector('#search-customer').addEventListener('keyup', onKeyUp);
    }

    if(window.location.pathname.includes('/insurence/index.html')){
        onLoadTableInfo("/insurence-table");
    }

    if(window.location.pathname.includes('/contract/index.html')){
        onLoadTableInfo("/contract-table");
    }
    
}

const siteLoader = () => 
{
    if(document.querySelector('#pageWrap').classList.contains('preload')){
        document.querySelector('#pageWrap').classList.remove('preload');
    }
}


const fixed = () =>
{
    let body = document.querySelector('body'); 

    if (window.pageYOffset > 0) {
       body.classList.add("sticky");
   } else {
       body.classList.remove("sticky");
   }
    window.onscroll = function() {
        if (window.pageYOffset > 0) {
           body.classList.add("sticky");
       } else {
           body.classList.remove("sticky");
       }
    }
} 


const onformControl = form =>
{
    let inputs = document.querySelectorAll('input');
    let errorFields = document.querySelectorAll('[data-field-error]');
    let listValueInput = [];

    errorFields.forEach((field) => {
        field.remove();
    })

    for(i in inputs){
        if(inputs[i].value === inputs[i].defaultValue && typeof inputs[i] === "object" && inputs[i].type !== "submit" && inputs[i].type !== "hidden"){
            let div = document.createElement("div");
            div.setAttribute("data-field-error", inputs[i].dataset.fieldName);
            div.innerText = inputs[i].dataset.error;
            if(inputs[i].dataset.error !== undefined){
                inputs[i].after(div);
            }
        }else if(inputs[i].value !== inputs[i].defaultValue && typeof inputs[i] === "object" && inputs[i].type !== "submit"){
            listValueInput.push(inputs[i]);
        }else if(inputs[i].name === "add"){
            if(listValueInput.length === inputs.length - 1){
                return true;
            }else{
                return false;
            } 
        }else{
            if(form.querySelector("button[type=submit]")){
                
                if(form.querySelector("input[data-not-reqiured]")){
                    let list_not_req =  form.querySelectorAll("input[data-not-reqiured]");

                    if(listValueInput.length === (inputs.length - list_not_req.length)){ 
                        return true;
                    }
                    
                }
                if(listValueInput.length === inputs.length){ 
                    return true;
                }
            }

            return false;         
        }

    }
}


const onLoginForm = form =>{
    form.addEventListener('submit', e => {
        e.preventDefault();
        
        if(onformControl(form)){
            $.ajax({
                url: "login",
                method: "POST",
                data: {
                    username : $('#username').val(),
                    password : $('#password').val(),
                },
                success: props =>{
                    switch(props){
                        case "/pages/error.html":
                            onShowNotification("Form Error", "You have entered incorrect data, please try again");
                            break;
                        default:
                            window.location.href = props;
                    }
                },
                fail : () => {
                    window.location.href = props;
                }
            });
        }
    });
}


const onRegisterForm = form =>{
    form.addEventListener('submit', e => {
        e.preventDefault();
        if(onformControl(form)){
            $.ajax({
                url: window.location.origin + "/register",
                method: "POST",
                data: {
                    username :          $('#username').val(),
                    password :          $('#password').val(),
                    repeatPassword :    $('#confirm-register-pass').val(),
                },
                success: props =>{
                    switch(props){
                        case "/pages/error.html":
                            onShowNotification("Form Error", "Password fields do not match");
                            break;
                        case "sql: email exist":
                            onShowNotification("SQL", "A user with this email already exists");
                            break;
                        default:
                            window.location.href = props;
                    }
                },
                fail : () => {
                    window.location.href = props;
                }
            });
        }
    });
}

const onCreateCustomer = form =>{
    form.addEventListener('submit', e => {
        e.preventDefault();
        if(onformControl(form)){
            
            $.ajax({
                url: window.location.origin + "/create-client",
                method: "POST",
                data: {
                    name :  $('#name').val(),
                    pin :   $('#pin').val(),
                    phone : $('#phone').val(),
                },
                
                success: props =>{
                    
                    switch(props){
                        case "session: employee missing":
                            onShowNotification("Session Error", "Аn error has occurred, please log in again");
                            break;
                        case "sql: pin exist":
                            onShowNotification("SQL", "A user with this Personal identification number already exists");
                            break;
                        default:
                            onShowNotification("Success", "You have successfully added the " + $('#name').val() + " to the system", "success");
                            setTimeout(() =>{
                                window.location.href = "/pages/customer/index.html#table";
                            }, 800)
                            
                    }
                },
                fail : () => {
                    window.location.href = props;
                }
            });
        }
    });
}

const onCreateInsurence = form =>{
    form.addEventListener('submit', e => {
        e.preventDefault();
        if(onformControl(form)){
            if($('#type').val() === 'null'){
                onShowNotification("Form Error", "Please Select type of insurence");
                return false;
            }
            $.ajax({
                url: window.location.origin + "/create-insurence",
                method: "POST",
                data: {
                    name :  $('#name').val().toUpperCase(),
                    type :  $('#type').val(),
                    price : $('#price').val(),
                    image : $('#image').val(),
                },
                
                success: props =>{
                    
                    switch(props){
                        case "session: employee missing":
                            onShowNotification("Session Error", "Аn error has occurred, please log in again");
                            break;
                            case "sql: insurence exist":
                                onShowNotification("SQL Error", "The insurance already exists");
                                break;
                        default:
                            onShowNotification("Success", "You have successfully added the " + $('#name').val() + " insurence to the system", "success");
                            setTimeout(() =>{
                                window.location.href = "/pages/insurence/index.html#table";
                            }, 800)
                            
                    }
                },
                fail : () => {
                    window.location.href = props;
                }
            });
        }
    });
}
const onCreateContract = form =>{
    form.addEventListener('submit', e => {
        e.preventDefault();
        if(onformControl(form)){
            if($('#pin').val() === 'null'){
                onShowNotification("Form Error", "Please Select client");
                return false;
            }else if($('#insurence-name').val() === 'null'){
                onShowNotification("Form Error", "Please Select insurence");
                return false;
            }
            let client = document.querySelector("[value='"+ $('#pin').val() +"']");
            let insurence = document.querySelector("[value='"+ $('#insurence-name').val() +"']");
            $.ajax({
                url: window.location.origin + "/create-contract",
                method: "POST",
                data: {
                    pin         :   client.getAttribute("value"),
                    cname       :   client.getAttribute("name"),
                    iname       :   $('#insurence-name').val(),
                    price       :   insurence.getAttribute("price"),
                    type        :   insurence.getAttribute("type"),
                    details     :   $('#details').val(),
                    singUpDate  :   $('#sing-up-date').val()
                },
                
                success: props =>{
                    
                    switch(props){
                        case "session: employee missing":
                            onShowNotification("Session Error", "Аn error has occurred, please log in again");
                            break;
                            case "sql: insurence exist":
                                onShowNotification("SQL Error", "The insurance already exists");
                                break;
                        default:
                            onShowNotification("Success", "You have successfully added the new ontract", "success");
                            setTimeout(() =>{
                                window.location.href = "/pages/contract/index.html#table";
                            }, 800)
                            
                    }
                },
                fail : () => {
                    window.location.href = props;
                }
            });
        }
    });
}

const onLogout = () =>{
    $.ajax({
        url: window.location.origin + "/logout",
        method: "POST",
        complete: props =>{
            window.location.href = '/';
        },
        fail : () => {
            location.reload();
        }
    });
}


const onExistUser = () => {
    $.ajax({
        url: window.location.origin + "/loggedUserId",
        method: "GET",
        complete : data =>{
            switch(data.status){
                case 200:
                    onRequestTemplate('Logout-btn.html', addLogoutBtn);
                    onRequestTemplate('Credentials-navi.html', addCredentialNavi);
                    break;
                default:
                    window.location.href = "/"
                    break;
            }
        }, fail : () => {
            location.reload();
        }
    });
}

const onDelete = (url, props) =>{

    $.ajax({
        url: window.location.origin + url,
        method: "DELETE",
        data : props,
        complete: props =>{
            switch (props.responseText){
                case 'session: employee missing':
                    onShowNotification("Session Error", "Аn error has occurred, please log in again");
                break;
                case 'sql: client missing':
                    onShowNotification("SQL Error", "You are trying to delete a non-existent client. Please reload");
                break;
                case 'done':
                    onShowNotification("Done", "You have successfully saved the recording", "success");
                    setTimeout(()=>{
                        location.reload();
                    }, 1000);
                break;
            }
        },
        fail : () => {
            location.reload();
        }
    });
}

const onRequestTemplate = (template, func) =>{
    $.ajax({
        url: window.location.origin + "/assets/templates/" + template,
        method: "GET",
        complete : data =>{
            switch(data.status){
                case 200:
                    func(data.responseText);
                    break;
                default:
                    console.log("function addLogoutBtn ajax status -> " + data.status);
                    break;
            }
        }, fail : () => {
            location.reload();
        }
    });
}


const addLogoutBtn = (content) =>{
    this.content = document.createElement('div');
    this.content.setAttribute('class', 'col-2');
    this.content.innerHTML = content;
    document.querySelector('#fixed-line').append(this.content);    
    document.querySelector('#logout-btn').addEventListener('click', onLogout);
}

const addDeleteBtn = (content) => {
    let template = document.createElement('div');
    template.setAttribute("class", "btn-box fixed");
    template.innerHTML = content;
    let tbody = document.querySelector("#table tbody");
    let tr_collection = tbody.children;
    tr_collection = Array.from(tr_collection);

    for( i in tr_collection){
        let cloning = template.cloneNode(true);
        if(tbody.getAttribute('data-table') == "customer"){}
        switch(tbody.getAttribute('data-table')){
            case 'customer':
                let src_pin = tr_collection[i].querySelector("[data-src=pin]");
                cloning.addEventListener('click', () =>{
                    onDelete("/customer-table/delete",
                        {
                            pin : src_pin.textContent
                        });
                });
            break;
            case 'insurence':
                const name = tr_collection[i].querySelector("[data-src=name]").textContent;
                const type = tr_collection[i].querySelector("[data-src=type]").textContent;
                cloning.addEventListener('click', () =>{
                    onDelete(
                    "/insurence-table/delete",
                    {
                        name : name,
                        type : type,
                    });
                });
            break;
            case 'contract':
                const pin = tr_collection[i].querySelector("[data-src=pin]").textContent;
                const insurence = tr_collection[i].querySelector("[data-src=insuranceName]").textContent;
                const duration = tr_collection[i].querySelector("[data-src=duration]").textContent;
                cloning.addEventListener('click', () =>{
                    onDelete(
                    "/contract-table/delete",
                    {
                        pin             : pin,
                        insurenceName   : insurence,
                        duration        : duration
                    });
                });
                break;
        }
        
        tr_collection[i].appendChild(cloning);
    }
}

const addCredentialNavi = (content) =>{



    this.content = document.createElement('div');
    this.content.setAttribute('class', 'fixed');
    this.content.innerHTML = content;
    document.body.append(this.content);
}

const addResultTable = (props) =>{
    let table = document.querySelector('tbody');
    
    props.map((result) => {
        let tr = document.querySelector('tbody tr').cloneNode(true);
        let new_tr = document.createElement("tr");
        let collection_td = tr.children;        
        collection_td = Array.from(collection_td);
        
        for( i in collection_td){
            let el_src = collection_td[i].dataset.src;
            collection_td[i].innerText = result[el_src];
            new_tr.appendChild(collection_td[i]);
        }

        table.appendChild(new_tr);
    });
    table.firstElementChild.remove();
}

const onShowNotification = (title, content, type) =>{
    let notification = document.querySelector("notifications");
    notification.querySelector("strong").innerText = title;
    notification.querySelector("p").innerText = content;
    switch(type){
        case "success":
            notification.classList.add("success")
            break;
        case "warning":
        notification.classList.add("warning")
        break;
        default:
            notification.classList.add("error")
            break;
    }

    if(notification.classList.contains("show-notification")){
        notification.classList.add("animation"); 
        setTimeout(() => {
           notification.classList.remove("animation");
           notification.classList.remove(type); 
        }, 900);
    }else{
        notification.classList.add("show-notification");
    }
    onClose(notification, [notification.querySelector("strong"), notification.querySelector("p")]);
}

const onClose = (field, props) => {
    field.querySelector('close').addEventListener('click', () => {
        field.classList.remove('show-notification');
        setTimeout(() => {
            props.forEach((elem) => {
                elem.innerText = "";
            });
        }, 300)
        
    })
}

const renderTableConditionData = (data) => {
    let table = document.querySelector('tbody');
    
    let tr_child = table.children;
    tr_child = Array.from(tr_child);
    
    if(tr_child.length === 0){
        table.appendChild(nullResults());
    }

    for( i = 1; i < tr_child.length; i++){
        tr_child[i].remove();
    }
    data.map((result) => {
        
        let tr = document.querySelector('tbody tr').cloneNode(true);
        let new_tr = document.createElement("tr");
        let collection_td = tr.children;        
        collection_td = Array.from(collection_td);
        
        for( i in collection_td){
            let el_src = collection_td[i].dataset.src;
            collection_td[i].innerText = result[el_src];
            new_tr.appendChild(collection_td[i]);
        }

        table.appendChild(new_tr);
    });
    table.firstElementChild.remove();

    if(table.querySelector('.btn-box')){
        let oldButtonsArray = table.querySelectorAll('.btn-box');
        oldButtonsArray = Array.from(oldButtonsArray);
        oldButtonsArray.forEach((btn) => {
            btn.remove();
        })
    }
    onRequestTemplate('Delete-btn.html', addDeleteBtn);
}
const credentials = (view = false) =>{
    $.ajax({
        url : window.location.origin + '/credentials',
        method : 'GET',
        complete : data => {
            
            switch (data.responseText) {
                case "ADMIN": 
                    onRequestTemplate('Delete-btn.html', addDeleteBtn);
                    
                    break;
                default:
                    if(view){
                        return true;
                        break;
                    }else{
                        window.location.href = "/pages/home.html";
                        break;
                    }  
            }
        },
        fail : () => {
            location.reload();
        }
    }); 
}

const onLoadTableInfo = (table_name, condition = "", addButtons) =>{
    $.ajax({
        url : window.location.origin + table_name + "/all",
        method : 'GET',
        complete : data => {
            if(data.status == 200){
                if(condition == ""){
                    addResultTable(data.responseJSON);
                    if(addButtons){
                        let table = document.querySelector('tbody');
                        if(table.querySelector('.btn-box')){
                            let oldButtonsArray = table.querySelectorAll('.btn-box');
                            oldButtonsArray = Array.from(oldButtonsArray);
                            oldButtonsArray.forEach((btn) => {
                                btn.remove();
                            })
                        }
                        onRequestTemplate('Delete-btn.html', addDeleteBtn);
                    }
                }else{
                    let dataCondition = [];

                    data.responseJSON.forEach((result) => {
                        let name = result.name.toUpperCase();
                        let conditionUp = condition.toUpperCase();
                        if(name.includes(conditionUp)){
                            dataCondition.push(result);
                        }
                    });

                    const coditionRender = (data) =>{
                        let table = document.querySelector('tbody');
                        let collection_row = table.children;        
                        
                        for(i = 1; i < collection_row.length; i++){
                            collection_row[i].remove();
                        }

                        renderTableConditionData(data);
                    };
                    coditionRender(dataCondition);
                }
            }
        },
        fail : () => {
            location.reload();
        }
    });
}

const loadDataInsurence = () =>{
    function inject(data){
        let select = document.querySelector('#insurence-name');
        data.forEach((result) =>{
            let option = document.createElement("option");
            option.setAttribute('value', result.name);
            option.setAttribute('price', result.price);
            option.setAttribute('type', result.type);
            option.innerText = result.name + " / " + result.type + " / $" + result.price;
            select.append(option);
        });
    }

    $.ajax({
        url : window.location.origin + "/insurence-table/all",
        method : 'GET',
        complete : data => {
            if(data.status == 200){
                inject(data.responseJSON);
            }
        },
        fail : () => {
            location.reload();
        }
    });
}

const loadDataClient = () => {

    function inject(data){
        let select = document.querySelector('#pin');

        data.forEach((result) =>{
            let option = document.createElement("option");
            option.setAttribute('value', result.pin);
            option.setAttribute('name', result.name);
            option.innerText = result.name + " / " + result.pin;
            select.append(option);
        });
    }

    $.ajax({
        url : window.location.origin + "/customer-table/all",
        method : 'GET',
        complete : data => {
            if(data.status == 200){
                inject(data.responseJSON);
            }
        },
        fail : () => {
            location.reload();
        }
    });
    
}

const nullResults = () => {

    let tr = document.createElement('tr');
    const attr = ["name","pin","phone"];

    for(i in attr){
        let td = document.createElement('td');
        td.setAttribute("data-src", attr[i]);
        tr.append(td);
    }
    return tr;

}

const onKeyUp = (e) =>{
    clearTimeout(timeout);
    let deleteButtons = false;

    if(e.target.value == "" && timeout != undefined){
        deleteButtons = true;
    }

    timeout = setTimeout(() =>{
        switch(e.target.name){
            case 'search-customer':
                onLoadTableInfo('/customer-table', e.target.value, deleteButtons);
                break;
        }
    }, 650)
   
}

window.addEventListener('load', aways());

