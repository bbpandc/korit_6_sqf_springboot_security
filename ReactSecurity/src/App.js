import { BrowserRouter, Route, Routes } from "react-router-dom";
import IndexPage from "./pages/IndexPage/IndexPage";
import UserJoinPage from "./pages/UserJoinPage/UserJoinPage";
import UserLoginPage from "./pages/UserLoginPage/UserLoginPage";
import { instance } from "./apis/util/instance";
import { useEffect, useState } from "react";
import { useQuery } from "react-query";

function App() {

    const accessTokenValid = useQuery(
        ["accessTokenValidQuery"], 
        async () => {
            return await instance.get("/auth/access", {
                params: {
                    accessToken: localStorage.getItem("accessToken")
                }
            });
        }, {
            retry: 0,
            onSuccess: response => {
                console.log(response.data);
            },
            onError: error => {
                console.error(error);
            }
        }
    );

    const userInfo = useQuery(
        ["userInfoQuery"],
        async () => {
            return await instance.get("/user/me");
        },
        {
            enabled: accessTokenValid.isSuccess && accessTokenValid.data?.data,
            onSuccess: response => {
                console.log(response);
            },
        }
    );

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={ <IndexPage /> }/>
                <Route path="/user/join" element={ <UserJoinPage /> }/>
                <Route path="/user/login" element={ <UserLoginPage /> }/>

                <Route path="/admin/*" element={ <></> }/>
                <Route path="/admin/*" element={ <h1>Not Found</h1> }/>
                <Route path="*" element={ <h1>Not Found</h1> }/>
            </Routes>
        </BrowserRouter>
    );
}

export default App;