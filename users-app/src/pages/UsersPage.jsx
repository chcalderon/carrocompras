import { useEffect } from "react";
import { UserModalForm } from "../components/UserModalForm";
import { UsersList } from "../components/UsersList";
import { useUsers } from "../hooks/useUsers";
import { useAuth } from "../auth/hooks/useAuth";

export const UsersPage = () => {

    const {
        users,
        visibleForm,
        isLoading,
        handlerOpenForm,
        getUsers,
    } = useUsers();

    const { login } = useAuth();

    useEffect(() => {
        getUsers();
    }, []);

    if (isLoading) {
        return (
            <div className="container my-4">
                <div className="d-flex align-items-center">
                    <strong>Cargando...</strong>
                    <div className="spinner-border ms-auto" role="status" aria-hidden="true"></div>
                </div>
            </div>
        )
    }
    return (
        <>

            {!visibleForm ||
                <UserModalForm />}
            <div className="container my-4">
                <h2>Users App</h2>
                <div className="row">
                    <div className="col">
                        {(visibleForm || !login.isAdmin) || <button
                            className="btn btn-primary my-2"
                            onClick={handlerOpenForm}>
                            Nuevo Usuario
                        </button>}

                        {
                            users.length === 0
                                ? <div className="alert alert-warning">No hay usuarios en el sistema!</div>
                                : <UsersList />
                        }
                    </div>
                </div>
            </div>
        </>
    );
}