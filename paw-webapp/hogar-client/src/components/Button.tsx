import { Link } from "react-router-dom"
import {Component} from "react";

export class Button extends Component<{ link: any, name: any }> {
    render() {
        let {link, name} = this.props;
        return (
            <Link to={link}>
                <button
                    className="bg-violet-300 w-2/5 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl border-solid border-transparent border-2 hover:border-purple-300">
                    {name}
                </button>
            </Link>
        )
    }
}

export default Button;