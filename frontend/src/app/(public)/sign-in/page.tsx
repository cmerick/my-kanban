import SignInFormSheet from "@/app/_components/signin-form-sheet";
import { Button } from "@/components/ui/button";

export default function SignIn() {
  return (
    <header className="flex justify-between items-center ">
      <h1 className="">Project Manager</h1>
      <SignInFormSheet trigger={<Button>Sign in</Button>} />
    </header>
  );
}
