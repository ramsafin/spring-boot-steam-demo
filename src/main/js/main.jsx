'use strict';


/*
    Chat
       - DialogPreview
           - MessageScrollList
             - Message
             - Message
             ...
           - MessageForm

       - DialogPreview
       ...
 */

const React = require('react');
const ReactDOM = require('react-dom');

let chats = [
    {chatID: 1},
    {chatID: 2},
    {chatID: 3},
];

let messages1 = [
    {msgId: 1, msg: 'Hello, guy!', sender: 1},
    {msgId: 2, msg: 'Hi!', sender: 2},
    {msgId: 3, msg: 'I need you opinion...', sender: 1},
    {msgId: 4, msg: 'How\' it going?', sender: 1},
];

/*
 TODO
 - where to save messages
 - make links and their handlers (open dialog, back to dialogs)
 - Socket, !Important
 */

class Chat extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            chats: chats
        }
    }

    render() {

        let previews = this.state.chats.map(chat => {
            return (<DialogPreview key={chat.chatID} lastMessage={chat.chatID}/>);
        });

        return (
            <div>
                <DialogEntry />
            </div>
        );
    }
}


class DialogPreview extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <h3>Last message : </h3>
                <p>Hello {this.props.lastMessage}</p>
            </div>
        );
    }
}


class DialogEntry extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            messages: messages1
        };
    }

    render() {
        let msgView = this.state.messages.map(m => {
            return (<Message key={m.msgId} msg={m.msg}/>);
        });

        return (
            <div>
                <h1>Hi! 1</h1>
                {msgView}
            </div>
        );
    }
}


class MessageScrollList extends React.Component {


}


class Message extends React.Component {

    render() {
        return (
            <div>
                {this.props.msg}
            </div>
        );
    }
}


ReactDOM.render(<Chat />, document.getElementById('content'));