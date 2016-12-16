'use strict';


const React = require('react');
const ReactDOM = require('react-dom');


const apiURL = '/api/chat/';

class Chat extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            messages: [],
            chatId: ''
        };

        this.fetchMessages = this.fetchMessages.bind(this);
        this.onMessageFormSubmitHandler = this.onMessageFormSubmitHandler.bind(this);
    }

    onMessageFormSubmitHandler(e, callback, msg) {
        console.log('message submit handler');

        let message = {
            chatId: this.state.chatId,
            sender: this.state.meName,
            msg: msg
        };

        console.log(`message to be send : ${JSON.stringify(message)}`);

        fetch(apiURL + 'addMessage',
            {
                method: 'POST',
                body: JSON.stringify(message, ['chatId', 'sender', 'msg'])
            })
            .then(resp => {
                return resp.json();
            })
            .then(json => {

                console.log('addMessage has been accomplished');

                let message = {
                    id: json.id,
                    date: json.date,
                    sender: json.sender,
                    text: json.msg
                };

                console.log(`message from server: ${JSON.stringify(message)}`);

                let messages = this.state.messages;

                messages.push(message);

                this.state.setState({messages: messages});

                callback(e, true);
            })
            .catch(err => {
                console.error(err);
                callback(e, false);
            });

    }

    componentDidMount() {

        console.log('loading messages for chat ...');

        fetch(apiURL + 'getChatId')
            .then(resp => {
                return resp.text();
            })
            .then(chat => {
                console.log(`chat id is ${chat}`);

                this.setState({chatId: chat});

                this.fetchMessages(this.state.chatId);

                this.apiInterval = setInterval(() => {
                    console.log('loading messages, 2000 ms');
                    this.fetchMessages(this.state.chatId);
                }, 30 * 1000); //milliseconds

            })
            .catch(err => {
                console.error(err);
            });
    }

    fetchMessages(chatId) {
        console.log('fetching messages ...');

        fetch(apiURL + chatId)
            .then(resp => {
                return resp.json();
            })
            .then(json => {

                console.log('messages have been received');

                this.setState({
                    me: json.meId,
                    meName: json.meName,
                    userId: json.userId,
                    userName: json.userName,
                    messages: json.messages.map(m => {
                        return {
                            id: m.id,
                            date: m.date,
                            text: m.msg,
                            sender: m.sender
                        }
                    })
                });
            })
            .catch(err => {
                console.error(err);
            });
    }

    componentWillUnmount() {
        console.log('clear interval');
        clearInterval(this.apiInterval);
    }

    render() {
        return (
            <div>
                {!this.state.messages || this.state.messages.length === 0 ?
                    <div>
                        <p>There is no messages yet</p>
                        <MessageForm onMessageFormSubmit={this.onMessageFormSubmitHandler}/>
                    </div>
                    :
                    <div>
                        <MessageScrollList messages={this.state.messages}/>
                        <MessageForm onMessageFormSubmit={this.onMessageFormSubmitHandler}/>
                    </div>
                }
            </div>
        );
    }
}


class MessageScrollList extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        return (
            <div className="scrollMessage">
                <ul className="messageList">
                    {!this.props.messages ?

                        <h3>There is no messages yet...</h3>
                        :

                        this.props.messages.map(m => {
                            return (
                                <Message key={m.id} message={m}/>
                            )
                        })
                    }
                </ul>
            </div>
        );
    }
}


class MessageForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            message: ''
        };
        this.onInputChange = this.onInputChange.bind(this);
        this.onFormSubmitCallback = this.onFormSubmitCallback.bind(this);
        this.onFormSubmit = this.onFormSubmit.bind(this);
    }

    onInputChange(e) {
        this.setState({message: e.target.value});
    };

    onFormSubmitCallback(e, status) {
        console.log('form submit callback');
        (status === true) ? e.target.reset() : console.error('can\'t send message');
    }

    onFormSubmit(e) {
        e.preventDefault();
        console.log('form has been submitted');
        this.props.onMessageFormSubmit(e, this.onFormSubmitCallback, this.state.message);
    }

    render() {
        return (
            <form onSubmit={this.onFormSubmit}>
                <textarea onChange={this.onInputChange} placeholder="Enter your message here...">

                </textarea>
                <button type="submit">Send message</button>
            </form>
        )
    }
}


class Message extends React.Component {

    render() {
        return (
            <li>
                <p>id : {this.props.message.id}</p>
                <p>at : {this.props.message.date}</p>
                <p>message : {this.props.message.text}</p>
                <p>by : {this.props.message.sender}</p>
            </li>
        );
    }
}


ReactDOM.render(<Chat />, document.getElementById('content'));